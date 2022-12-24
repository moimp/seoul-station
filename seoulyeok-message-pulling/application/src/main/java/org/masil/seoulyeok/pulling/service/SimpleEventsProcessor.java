package org.masil.seoulyeok.pulling.service;

import com.masil.commons.clocks.Clocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.pulling.*;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPipelineEventsPort;
import org.masil.seoulyeok.ref.pipeline.PipelineEvent;
import org.masil.seoulyeok.util.Tuple;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleEventsProcessor {

    private final PulledOffsetFinder pulledOffsetFinder;
    private final PulledOffsetUpdater pulledOffsetUpdater;
    private final NextEventsSelector nextEventsSelector;
    private final EventsMessageToRelaySender eventsMessageToRelaySender;
    private final LoadPipelineEventsPort loadPipelineEventsPort;

    public void next() {

        List<PulledOffset> onProgressPulledOffsets = pulledOffsetFinder.findAllOnProgressPulledOffset();

        if (onProgressPulledOffsets.isEmpty()) {
            return;
        }

        Map<DetailedPulledOffset, List<Event>> pulledOffsetAndEventsMap = getPulledOffsetKeyAndEventsMap(onProgressPulledOffsets);

        pulledOffsetAndEventsMap.forEach((offset, events) -> {
            DestinationId destinationId = offset.getDestinationId();

            try {
                eventsMessageToRelaySender.send(destinationId, events);

                if (events.isEmpty()) {
                    return;
                }

                Event event = events.get(events.size() - 1);
                offset.updateUptoDate(Position.of(event.getOccurredAt()));

            } catch (Exception e) {
                log.error("Couldn't send EventMessage to Rely ErrorMessage " + e.getMessage());
                offset.exit();
            }
            pulledOffsetUpdater.update(offset);
        });
    }

    private Map<DetailedPulledOffset, List<Event>> getPulledOffsetKeyAndEventsMap(List<PulledOffset> onProgressPulledOffsets) {
        return onProgressPulledOffsets.stream()
                .map(pulledOffset -> {
                    PipelineEvent pipelineEvent = loadPipelineEventsPort.get(pulledOffset.pipelineId());

                    //TODO 동작되는 소프트웨어를 위해 이벤트타입만 적용될 수 있도록 한다. 추후 변경되어져야 한다.
                    List<String> targetEvents = pipelineEvent.getTargetEventTypes();
                    LocalDateTime startingPointAt = pipelineEvent.getStartingPoint();
                    Position theLatestPosition = pulledOffset.getTheLatestPositionBetween(startingPointAt);

                    return new DetailedPulledOffset(pulledOffset.id(),
                            theLatestPosition,
                            Clocks.now(),
                            pulledOffset.destinationId(),
                            TargetEvents.of(targetEvents),
                            Size.of(pipelineEvent.getSize()),
                            pulledOffset.status());

                })
                .filter(DetailedPulledOffset::isOnProcessed)
                .map(detailedPulledOffset ->
                        Tuple.of(detailedPulledOffset, nextEventsSelector.getNextEvents(detailedPulledOffset)))
                .collect(Collectors.toMap(
                        Tuple::getLeft,
                        Tuple::getRight
                ));
    }
}

package org.masil.seoulyeok.pulling.incoming.persistence;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.events.EventId;
import org.masil.seoulyeok.pulling.DetailedPulledOffset;
import org.masil.seoulyeok.pulling.Position;
import org.masil.seoulyeok.pulling.port.outgoing.LoadEventsPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventsPersistenceAdapter implements LoadEventsPort {

    private final SpringDataEventRepository eventRepository;

    @Override
    public List<Event> findNextEventsByPulledOffset(DetailedPulledOffset detailedPulledOffset) {

        Position position = detailedPulledOffset.getPosition();
        List<String> targetEvents = detailedPulledOffset.getTargetEvents().getValue();
        Long size = detailedPulledOffset.getSize().getValue();

        List<EventsJdbcEntity> eventsJdbcEntities = eventRepository.findAllEventsFromLatestOccurredAtAndSize(targetEvents, position.getValue(), size);

        return eventsJdbcEntities.stream()
                .map(this::convertDomain)
                .collect(Collectors.toList());
    }

    private Event convertDomain(EventsJdbcEntity entity) {
        return Event.of(EventId.of(entity.getId()),
                entity.getEventType(),
                entity.getPayload().getValue(),
                entity.getPayloadVersion(),
                entity.getOccurredAt()
        );
    }
}

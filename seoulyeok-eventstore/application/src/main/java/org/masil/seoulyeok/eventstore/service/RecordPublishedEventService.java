package org.masil.seoulyeok.eventstore.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.UseCase;
import org.masil.seoulyeok.eventstore.DomainEventId;
import org.masil.seoulyeok.eventstore.Event;
import org.masil.seoulyeok.eventstore.port.incoming.RecordPublishedEventUseCase;
import org.masil.seoulyeok.eventstore.port.incoming.RecordedEventCommend;
import org.masil.seoulyeok.eventstore.port.outgoing.LoadEventPort;
import org.masil.seoulyeok.eventstore.port.outgoing.RecordEventPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RecordPublishedEventService implements RecordPublishedEventUseCase {

    private final LoadEventPort loadEventPort;
    private final RecordEventPort recordEventPort;

    @Override
    public boolean store(RecordedEventCommend commend) {
        Long messageId = commend.getId();
        if (loadEventPort.existsEvent(messageId)) {
            throw new DuplicatedEventException("MessageId is " + messageId);
        }
        Event event = Event.of(DomainEventId.of(commend.getId()),
                commend.getEventType(),
                commend.getPayload(),
                commend.getVersion(),
                commend.getOccurredAt());
        recordEventPort.record(event);
        return true;
    }

    @Override
    public boolean store(List<RecordedEventCommend> commends) {
        List<Event> events = commends.stream()
                .map(commend -> Event.of(DomainEventId.of(commend.getId()),
                        commend.getEventType(),
                        commend.getPayload(),
                        commend.getVersion(),
                        commend.getOccurredAt())
                )
                .collect(Collectors.toList());

        recordEventPort.record(events);
        return true;
    }
}

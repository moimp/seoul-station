package org.masil.seoulyeok.eventstore.outgoing.persistence;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.PersistenceAdapter;
import org.masil.seoulyeok.eventstore.DomainEventId;
import org.masil.seoulyeok.eventstore.Event;
import org.masil.seoulyeok.eventstore.port.outgoing.DeleteEventPort;
import org.masil.seoulyeok.eventstore.port.outgoing.LoadEventPort;
import org.masil.seoulyeok.eventstore.port.outgoing.RecordEventPort;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class EventPersistenceAdapter implements LoadEventPort, RecordEventPort, DeleteEventPort {

    private final SpringDataEventRepository eventRepository;

    @Override
    public Event loadEvent(Long messageId) {
        EventJdbcEntity entity = eventRepository.findById(messageId).orElseThrow();
        return Event.of(DomainEventId.of(entity.getId()),
                entity.getEventType(),
                entity.getPayload().getValue(),
                entity.getPayloadVersion(),
                entity.getOccurredAt()
        );
    }

    @Override
    public boolean existsEvent(Long messageId) {
        return eventRepository.existsById(messageId);
    }

    @Override
    public void record(Event event) {
        EventJdbcEntity entity = EventJdbcEntity.create(
                event.getId().get(),
                event.getEventType(),
                EventPayload.of(event.getPayload()),
                event.getPayloadVersion(),
                event.getOccurredAt());
        eventRepository.save(entity);
    }

    @Override
    public void record(List<Event> events) {
        eventRepository.saveAll(events.stream()
                .map(e -> EventJdbcEntity.create(
                        e.getId().get(),
                        e.getEventType(),
                        EventPayload.of(e.getPayload()),
                        e.getPayloadVersion(),
                        e.getOccurredAt()))
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public void delete(DomainEventId eventId) {
        eventRepository.deleteById(eventId.get());
    }
}

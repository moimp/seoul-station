package org.masil.seoulyeok.eventstore;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Objects;

@Value(staticConstructor = "of")
public class Event {
    DomainEventId id;
    String eventType;
    String payload;
    String payloadVersion;

    LocalDateTime occurredAt;

    public Event(DomainEventId id, String eventType, String payload, String payloadVersion, LocalDateTime occurredAt) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(payloadVersion);
        this.id = id;
        this.eventType = eventType;
        this.payload = payload;
        this.payloadVersion = payloadVersion;
        this.occurredAt = occurredAt;
    }
}

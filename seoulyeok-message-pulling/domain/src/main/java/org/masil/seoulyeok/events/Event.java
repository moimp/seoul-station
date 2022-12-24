package org.masil.seoulyeok.events;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Objects;

@Value(staticConstructor = "of")
public class Event {
    EventId id;
    String eventType;
    String payload;
    String payloadVersion;

    LocalDateTime occurredAt;

    private Event(EventId id, String eventType, String payload, String payloadVersion, LocalDateTime occurredAt) {
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

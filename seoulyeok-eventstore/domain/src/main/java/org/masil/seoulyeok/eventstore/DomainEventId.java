package org.masil.seoulyeok.eventstore;

import lombok.Value;

@Value(staticConstructor = "of")
public class DomainEventId {
    Long value;

    public Long get() {
        return value;
    }
}

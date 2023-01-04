package org.masil.seoulyeok.events.destination;

import com.likelen.identifier.core.LongId;
import lombok.Value;

@Value(staticConstructor = "of")
public class DestinationId implements LongId {
    Long value;

    @Override
    public Long get() {
        return value;
    }
}

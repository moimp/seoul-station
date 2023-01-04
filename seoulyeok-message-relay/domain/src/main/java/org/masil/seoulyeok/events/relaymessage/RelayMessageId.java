package org.masil.seoulyeok.events.relaymessage;

import com.likelen.identifier.core.LongId;
import lombok.Value;

@Value(staticConstructor = "of")
public class RelayMessageId implements LongId {
    Long value;

    @Override
    public Long get() {
        return value;
    }
}

package com.trevari.messages;

import com.trevari.identity.core.LongId;
import lombok.Value;

@Value
public class MessageId implements LongId {
    Long value;

    @Override
    public Long get() {
        return value;
    }
}

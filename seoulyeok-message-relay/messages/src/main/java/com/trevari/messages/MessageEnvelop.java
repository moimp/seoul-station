package com.trevari.messages;

import javax.validation.constraints.NotNull;

public interface MessageEnvelop<T> {

    @NotNull
    Long getMessageId();
    @NotNull
    T getPayload();
}

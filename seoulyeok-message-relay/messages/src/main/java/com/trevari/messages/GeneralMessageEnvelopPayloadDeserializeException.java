package com.trevari.messages;

import lombok.Getter;

@Getter
public class GeneralMessageEnvelopPayloadDeserializeException extends RuntimeException {
    private final GeneralMessageEnvelop<String> messageEnvelop;

    public GeneralMessageEnvelopPayloadDeserializeException(GeneralMessageEnvelop<String> messageEnvelop, Exception e) {
        super(e);
        this.messageEnvelop = messageEnvelop;
    }
}

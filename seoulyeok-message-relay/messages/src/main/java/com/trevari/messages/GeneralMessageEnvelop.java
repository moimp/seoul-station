package com.trevari.messages;

import com.trevari.identity.generator.LongIdGeneratorHolder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralMessageEnvelop<T> implements MessageEnvelop<T> {
    Long messageId;
    T payload;

    public GeneralMessageEnvelop(T payload) {
        this.messageId = LongIdGeneratorHolder.get().gen(MessageId.class).get();
        this.payload = payload;
    }
}

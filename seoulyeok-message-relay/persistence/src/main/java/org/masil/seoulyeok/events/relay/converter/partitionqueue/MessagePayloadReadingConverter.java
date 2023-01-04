package org.masil.seoulyeok.events.relay.converter.partitionqueue;

import org.masil.seoulyeok.events.relay.util.Serializer;
import com.trevari.events.relaymessage.MessagePayload;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public enum MessagePayloadReadingConverter implements Converter<PGobject, MessagePayload> {
    INSTANCE;

    @Override
    public MessagePayload convert(PGobject pgObject) {
        String source = pgObject.getValue();
        return Serializer.getInstance().deserialize(source, MessagePayload.class);
    }
}

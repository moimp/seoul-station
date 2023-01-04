package org.masil.seoulyeok.events.relay.converter.partitionqueue;

import org.masil.seoulyeok.events.relay.util.Serializer;
import com.trevari.events.relaymessage.MessagePayload;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.SQLException;

@WritingConverter
public enum MessagePayloadWritingConverter implements Converter<MessagePayload, PGobject> {
    INSTANCE;

    @Override
    public PGobject convert(MessagePayload source) {
        String json = Serializer.getInstance().serialize(source);

        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        try {
            jsonObject.setValue(json);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return jsonObject;
    }
}

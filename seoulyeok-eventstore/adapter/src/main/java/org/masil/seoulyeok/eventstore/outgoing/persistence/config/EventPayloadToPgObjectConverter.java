package org.masil.seoulyeok.eventstore.outgoing.persistence.config;

import org.masil.seoulyeok.common.Serializer;
import org.masil.seoulyeok.eventstore.outgoing.persistence.EventPayload;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.SQLException;

@WritingConverter
public class EventPayloadToPgObjectConverter implements Converter<EventPayload, PGobject> {

    @Override
    public PGobject convert(EventPayload source) {
        String value = source.getValue();
        String serialize = Serializer.getInstance().serialize(value);
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        try {
            pGobject.setValue(serialize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pGobject;
    }
}

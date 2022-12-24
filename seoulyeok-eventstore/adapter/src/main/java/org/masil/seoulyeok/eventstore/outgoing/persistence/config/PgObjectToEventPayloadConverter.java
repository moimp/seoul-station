package org.masil.seoulyeok.eventstore.outgoing.persistence.config;

import org.masil.seoulyeok.eventstore.outgoing.persistence.EventPayload;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class PgObjectToEventPayloadConverter implements Converter<PGobject, EventPayload> {
    @Override
    public EventPayload convert(PGobject source) {
        String value = source.getValue();
        return EventPayload.of(value);
    }
}

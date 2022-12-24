package org.masil.seoulyeok.pulling.incoming.persistence.config;

import org.masil.seoulyeok.common.Serializer;
import org.masil.seoulyeok.pulling.TargetEvents;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StringToTargetEventsConverter implements Converter<PGobject, TargetEvents> {

    @Override
    public TargetEvents convert(PGobject source) {
        String value = source.getValue();
        return Serializer.getInstance().deserialize(value, TargetEvents.class);
    }
}

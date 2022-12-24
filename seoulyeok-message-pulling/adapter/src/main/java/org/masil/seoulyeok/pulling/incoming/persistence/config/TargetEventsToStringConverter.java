package org.masil.seoulyeok.pulling.incoming.persistence.config;

import org.masil.seoulyeok.common.Serializer;
import org.masil.seoulyeok.pulling.TargetEvents;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.SQLException;

@WritingConverter
public class TargetEventsToStringConverter implements Converter<TargetEvents, PGobject> {

    @Override
    public PGobject convert(TargetEvents source) {
        String serialize = Serializer.getInstance().serialize(source);
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

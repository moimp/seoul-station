package org.masil.seoulyeok.events.relay.converter.destination;

import com.trevari.events.destination.DestinationId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class LongToDestinationIdConverter implements Converter<Long, DestinationId> {
    @Override
    public DestinationId convert(Long source) {
        return DestinationId.of(source);
    }
}

package org.masil.seoulyeok.events.relay.converter.destination;

import com.trevari.events.destination.DestinationId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class DestinationIdToLongConverter implements Converter<DestinationId, Long> {
    @Override
    public Long convert(DestinationId source) {
        return source.getValue();
    }
}

package org.masil.seoulyeok.events.relay.converter.destination;

import com.trevari.events.destination.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class AddressToStringConverter implements Converter<Address, String> {
    @Override
    public String convert(Address source) {
        return source.getValue();
    }
}

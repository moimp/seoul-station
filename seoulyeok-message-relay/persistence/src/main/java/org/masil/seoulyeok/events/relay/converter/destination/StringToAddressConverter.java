package org.masil.seoulyeok.events.relay.converter.destination;

import com.trevari.events.destination.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToAddressConverter implements Converter<String, Address> {
    @Override
    public Address convert(String source) {
        return Address.of(source);
    }
}

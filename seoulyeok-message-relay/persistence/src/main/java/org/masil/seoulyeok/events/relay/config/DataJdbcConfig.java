package org.masil.seoulyeok.events.relay.config;

import org.masil.seoulyeok.events.relay.converter.destination.AddressToStringConverter;
import org.masil.seoulyeok.events.relay.converter.destination.DestinationIdToLongConverter;
import org.masil.seoulyeok.events.relay.converter.destination.LongToDestinationIdConverter;
import org.masil.seoulyeok.events.relay.converter.destination.StringToAddressConverter;
import com.trevari.events.relay.converter.partitionqueue.*;

import java.util.ArrayList;
import java.util.List;

import org.masil.seoulyeok.events.relay.converter.partitionqueue.LongToRelayMessageIdConverter;
import org.masil.seoulyeok.events.relay.converter.partitionqueue.MessagePayloadReadingConverter;
import org.masil.seoulyeok.events.relay.converter.partitionqueue.MessagePayloadWritingConverter;
import org.masil.seoulyeok.events.relay.converter.partitionqueue.RelayMessageIdToLongConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Configuration
@EnableJdbcAuditing
public class DataJdbcConfig extends AbstractJdbcConfiguration {

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(this.getCustomConverters());
    }

    private List<Converter<?, ?>> getCustomConverters() {
        List<Converter<?, ?>> converters = new ArrayList<>();

        converters.add(new DestinationIdToLongConverter());
        converters.add(new LongToDestinationIdConverter());

        converters.add(new AddressToStringConverter());
        converters.add(new StringToAddressConverter());

        converters.add(new RelayMessageIdToLongConverter());
        converters.add(new LongToRelayMessageIdConverter());

        converters.add(MessagePayloadReadingConverter.INSTANCE);
        converters.add(MessagePayloadWritingConverter.INSTANCE);
        return converters;
    }
}

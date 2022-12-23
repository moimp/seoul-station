package org.masil.seoulyeok.eventstore.outgoing.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.util.Arrays;
import java.util.List;

@EnableJdbcAuditing
@Configuration
public class DataJdbcConfig extends AbstractJdbcConfiguration {
    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        List<?> converters = Arrays.asList(
                new PgObjectToEventPayloadConverter(), new EventPayloadToPgObjectConverter()
        );
        return new JdbcCustomConversions(converters);
    }
}

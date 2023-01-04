package org.masil.seoulyeok.events.relay.config;

import com.trevari.identity.generator.IdGeneratorFactory;
import com.trevari.identity.generator.LongIdGenerator;
import com.trevari.identity.generator.LongIdGeneratorHolder;
import com.trevari.identity.generator.LongValueGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@RequiredArgsConstructor
public class LongIdGeneratorFactory extends AbstractFactoryBean<LongIdGenerator> {

    @Getter
    @Setter
    @Value("${id-generator.url}")
    private String url;

    private final Environment environment;

    @Override
    public Class<?> getObjectType() {
        return LongIdGenerator.class;
    }

    @Override
    protected LongIdGenerator createInstance() {
        IdGeneratorFactory idGeneratorFactory;
        if (Arrays.asList(environment.getActiveProfiles()).contains("production")) {
            idGeneratorFactory = new IdGeneratorFactory(url);
        } else {
            idGeneratorFactory = new IdGeneratorFactory(new DummyLongValueGenerator());
        }
        LongIdGenerator longIdGenerator = idGeneratorFactory.create();
        LongIdGeneratorHolder.set(longIdGenerator);
        return longIdGenerator;
    }

    private static class DummyLongValueGenerator implements LongValueGenerator {
        static final long SOURCE = 10000000;
        static final long BOUND = 99999999;

        @Override
        public long gen() {
            return ThreadLocalRandom.current().nextLong(SOURCE, BOUND);
        }
    }
}

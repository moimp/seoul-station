package org.masil.seoulyeok.events.relay.application.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RelayProcessorConfig {

    @Value("${relay.batch.size}")
    @Getter
    private int batchSize;

}

package org.masil.seoulyeok.pulling.outgoing.http.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RelayRequesterConfig {
    @Getter
    @Value("${message-relay.url}")
    private String baseUrl;

}

package org.masil.seoulyeok.pulling.outgoing.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.masil.seoulyeok.pulling.outgoing.http.config.RelayRequesterConfig;
import org.masil.seoulyeok.pulling.port.outgoing.MessageRelayHttpRequestPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MessageRelayRequestAdapter implements MessageRelayHttpRequestPort {

    private final RestTemplate restTemplate;
    private final RelayRequesterConfig config;

    @Override
    public boolean send(Long destinationId, String payload, String payloadVersion) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        HttpEntity<RelayRequest> relayRequestHttpEntity =
                new HttpEntity<>(RelayRequest.of(destinationId, payload, payloadVersion), headers);

        String url = config.getBaseUrl() + "/apis/events/relay";

        ResponseEntity<Result> exchange = restTemplate.exchange(url, HttpMethod.POST, relayRequestHttpEntity, Result.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            Result body = exchange.getBody();
            return body.getCode() >= 0;
        }
        return false;
    }

    @Data
    private static class Result {

        @JsonProperty("code")
        private Integer code;

        @JsonProperty("msg")
        private String msg;
    }

    @Value(staticConstructor = "of")
    private static class RelayRequest {

        Long destinationId;
        String payload;
        String payloadVersion;
    }

}

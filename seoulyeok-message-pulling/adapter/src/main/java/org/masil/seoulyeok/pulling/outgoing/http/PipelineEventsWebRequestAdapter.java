package org.masil.seoulyeok.pulling.outgoing.http;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.PipelineId;
import org.masil.seoulyeok.pulling.outgoing.http.config.PipelineEventConfig;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPipelineEventsPort;
import org.masil.seoulyeok.ref.pipeline.PipelineEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PipelineEventsWebRequestAdapter implements LoadPipelineEventsPort {

    private final RestTemplate restTemplate;
    private final PipelineEventConfig config;

    @Override
    public PipelineEvent get(PipelineId pipelineId) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity<>(headers);


        String urlFormat = String.format("/apis/pipelines/%s/events", pipelineId.getValue());
        ResponseEntity<PipelineEvent> exchange = restTemplate.exchange(config.getBaseUrl() + urlFormat, HttpMethod.GET, entity, PipelineEvent.class);

        if (exchange.getStatusCode().is2xxSuccessful() == false) {
            throw new IllegalStateException("Cannot request pipeline url: " + urlFormat);
        }
        return exchange.getBody();
    }
}

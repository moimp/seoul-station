package org.masil.seoulyeok.eventstore.incoming.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.masil.seoulyeok.common.WebAdapter;
import org.masil.seoulyeok.eventstore.port.incoming.RecordPublishedEventUseCase;
import org.masil.seoulyeok.eventstore.port.incoming.RecordedEventCommend;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.masil.seoulyeok.common.Constant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@WebAdapter
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE,
        params = {VERSION + IS + V_0_1_0})
@RequiredArgsConstructor
public class StoreEventsController {

    private final RecordPublishedEventUseCase recordUseCase;

    @PostMapping(value = "/apis/events")
    public ResponseEntity<EventsResponse> storeEvent(@RequestBody EventsRequest value) {
        RecordedEventCommend commend = new RecordedEventCommend(Long.parseLong(value.getId()),
                value.getType(),
                value.getPayload(),
                value.getVersion(),
                value.getOccurredAt()
        );
        if (!recordUseCase.store(commend)) {
            return ResponseEntity.badRequest().body(EventsResponse.of((value.getId())));
        }

        return ResponseEntity.ok().body(EventsResponse.of((value.getId())));
    }

    @PostMapping(value = "/apis/events/bulk")
    public ResponseEntity<EventsResponses> storeEvents(@RequestBody List<EventsRequest> value) {

        List<RecordedEventCommend> eventCommends = value.stream()
                .map(e -> new RecordedEventCommend(Long.parseLong(e.getId()),
                        e.getType(),
                        e.getPayload(),
                        e.getVersion(),
                        e.getOccurredAt()
                )).collect(Collectors.toList());


        if (!recordUseCase.store(eventCommends)) {
            return ResponseEntity.badRequest()
                    .body(EventsResponses.of(value.stream().map(a -> a.id).collect(Collectors.toList())));
        }

        return ResponseEntity.ok()
                .body(EventsResponses.of(value.stream().map(a -> a.id).collect(Collectors.toList())));
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class EventsRequests {
        private List<EventsRequest> events;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class EventsRequest {
        private String id;
        private String payload;
        private String type;
        private String version;
        private LocalDateTime occurredAt;
    }

    @Value(staticConstructor = "of")
    private static class EventsResponse {
        String successMessageId;
    }

    @Value(staticConstructor = "of")
    private static class EventsResponses {
        List<String> successMessageIds;
    }
}


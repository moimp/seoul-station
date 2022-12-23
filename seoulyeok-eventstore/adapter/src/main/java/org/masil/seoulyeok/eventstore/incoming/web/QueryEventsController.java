package org.masil.seoulyeok.eventstore.incoming.web;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.WebAdapter;
import org.masil.seoulyeok.eventstore.Event;
import org.masil.seoulyeok.eventstore.port.incoming.GetEventCommand;
import org.masil.seoulyeok.eventstore.port.incoming.GetEventUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.masil.seoulyeok.common.Constant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebAdapter
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE,
        params = {VERSION + IS + V_0_1_0})
@RequiredArgsConstructor
public class QueryEventsController {

    private final GetEventUseCase useCase;

    @GetMapping(value = "/apis/events/{eventId}")
    public ResponseEntity<Event> storeEvent(@PathVariable("eventId") Long eventId) {
        Event event = useCase.query(GetEventCommand.of(eventId));
        return ResponseEntity.ok(event);
    }
}

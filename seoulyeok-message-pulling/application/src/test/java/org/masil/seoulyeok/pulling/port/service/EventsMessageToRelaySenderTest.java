package org.masil.seoulyeok.pulling.port.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.events.EventId;
import org.masil.seoulyeok.pulling.DestinationId;
import org.masil.seoulyeok.pulling.config.Serializer;
import org.masil.seoulyeok.pulling.port.outgoing.MessageRelayHttpRequestPort;
import org.masil.seoulyeok.pulling.service.EventsMessageToRelaySender;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventsMessageToRelaySenderTest {

    public static final long ANY = 1L;
    public static final DestinationId DESTINATION_ID = DestinationId.of(ANY);
    public static final String ANY2 = "0.1.0";
    public static final long ANY_ID = 1004L;

    @InjectMocks
    EventsMessageToRelaySender sut;

    @Mock
    MessageRelayHttpRequestPort relayRequest;

    @Test
    void relay_에_전달하는_payload_는_eventId_json_이다() {
        Event eventBeSend = eventById(ANY_ID);
        List<Event> events = toList(eventBeSend);

        sut.send(DESTINATION_ID, events);

        verify(relayRequest).send(ANY,
                json(eventBeSend.getId()),
                ANY2);
    }

    @Test
    void n_개가_전달된다() {
        List<Event> events = toList(eventById(1L), eventById(2L));

        sut.send(DESTINATION_ID, events);

        verify(relayRequest).send(ANY, json(EventId.of(1L)), ANY2);
        verify(relayRequest).send(ANY, json(EventId.of(2L)), ANY2);
    }

    private String json(EventId id) {
        return Serializer.getInstance().serialize(id);
    }

    private List<Event> toList(Event ... event) {
        return Arrays.asList(event);
    }

    private Event eventById(Long id) {
        return Event.of(EventId.of(id), "any_type", "any_payload", "0.1.0", null);
    }
}

package org.masil.seoulyeok.pulling.service;

import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.pulling.DestinationId;
import org.masil.seoulyeok.pulling.config.Serializer;
import org.masil.seoulyeok.pulling.port.outgoing.MessageRelayHttpRequestPort;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class EventsMessageToRelaySender {

    private final MessageRelayHttpRequestPort sender;

    public EventsMessageToRelaySender(MessageRelayHttpRequestPort sender) {
        this.sender = sender;
    }

    public void send(DestinationId id, List<Event> events) {
        //TODO 이벤트릴레이와 통신하기 위한 api version
        String payloadVersion = "0.1.0";
        events.forEach(e -> {
            String payload = Serializer.getInstance().serialize(e.getId());
            log.info(payload);
            sender.send(id.getValue(), payload, payloadVersion);
        });
    }
}


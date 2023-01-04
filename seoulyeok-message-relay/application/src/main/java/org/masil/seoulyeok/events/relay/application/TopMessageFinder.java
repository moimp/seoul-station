package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relay.port.out.QueryRelayMessagePort;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopMessageFinder {

    private final QueryRelayMessagePort queryRelayMessagePort;

    public TopRelayMessage find(DestinationId destinationId) {
        if (!queryRelayMessagePort.existsByDestinationId(destinationId)) {
            return TopRelayMessage.EMPTY;
        }
        return queryRelayMessagePort.findTopMessageByDestinationId(destinationId);
    }
}


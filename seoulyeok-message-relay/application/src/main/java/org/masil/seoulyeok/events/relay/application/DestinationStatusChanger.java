package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relay.port.out.CommandDestinationPort;
import org.masil.seoulyeok.events.relay.port.out.LoadDestinationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationStatusChanger {

    private final LoadDestinationPort loadPort;
    private final CommandDestinationPort commandPort;

    public void inactivateBy(DestinationId destinationId) {
        Destination destination = loadPort.loadById(destinationId);
        destination.inactive();

        commandPort.update(destination);
    }
}

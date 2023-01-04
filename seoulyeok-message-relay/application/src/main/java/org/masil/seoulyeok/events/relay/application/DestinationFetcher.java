package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.relay.port.out.LoadDestinationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationFetcher {

    private final LoadDestinationPort loadPort;

    public List<Destination> findAllActiveDestination() {
        return loadPort.loadAllActiveDestination();
    }
}

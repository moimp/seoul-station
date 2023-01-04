package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.relay.models.RelayRequest;

public interface MessageSender {
    void doWork(RelayRequest relayRequest);
}

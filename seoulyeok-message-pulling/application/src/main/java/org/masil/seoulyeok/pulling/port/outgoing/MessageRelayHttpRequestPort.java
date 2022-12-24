package org.masil.seoulyeok.pulling.port.outgoing;

public interface MessageRelayHttpRequestPort {
    boolean send(Long destinationId,
                 String payload,
                 String payloadVersion);


}

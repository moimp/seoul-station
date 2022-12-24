package org.masil.seoulyeok.pulling.outgoing.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelayRequest {

    Long destinationId;
    String payload;
    String payloadVersion;
}

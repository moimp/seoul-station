package org.masil.seoulyeok.events.relay;

import lombok.Value;

@Value(staticConstructor = "of")
public class ReceivedEventMessage {
    Long destinationId;
    String payload;
    String payloadVersion;
}

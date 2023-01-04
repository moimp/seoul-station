package org.masil.seoulyeok.events.relaymessage;

import lombok.Value;

@Value(staticConstructor = "of")
public class RelayMessageModel {
    Long messageId;
    String payload;
    String createdAt;
    String payloadVersion;
}

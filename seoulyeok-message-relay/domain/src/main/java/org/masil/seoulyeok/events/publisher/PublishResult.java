package org.masil.seoulyeok.events.publisher;

import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;

import java.time.LocalDateTime;

public interface PublishResult {

    RelayMessageId getRelayMessageId();
    boolean isSuccess();
    DestinationId getDestinationId();
    LocalDateTime getPublishedAt();

}

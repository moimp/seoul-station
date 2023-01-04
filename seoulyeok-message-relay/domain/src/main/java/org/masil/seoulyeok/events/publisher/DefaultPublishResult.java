package org.masil.seoulyeok.events.publisher;

import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
public class DefaultPublishResult implements PublishResult {

    public static DefaultPublishResult success(RelayMessageId relayMessageId, DestinationId destinationId, LocalDateTime publishedAt) {
        return new DefaultPublishResult(relayMessageId, destinationId, publishedAt, true);
    }

    public static DefaultPublishResult fail(RelayMessageId relayMessageId, DestinationId destinationId, LocalDateTime publishedAt) {
        return new DefaultPublishResult(relayMessageId, destinationId, publishedAt, false);
    }

    private final RelayMessageId relayMessageId;
    private final DestinationId destinationId;
    private final LocalDateTime publishedAt;
    private final boolean success;

    @Override
    public RelayMessageId getRelayMessageId() {
        return relayMessageId;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public DestinationId getDestinationId() {
        return destinationId;
    }

    @Override
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}

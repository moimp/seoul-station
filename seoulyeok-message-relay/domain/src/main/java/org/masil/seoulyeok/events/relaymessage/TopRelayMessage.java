package org.masil.seoulyeok.events.relaymessage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

import lombok.ToString;
import org.masil.seoulyeok.events.destination.DestinationId;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TopRelayMessage {

    public static final TopRelayMessage EMPTY = new TopRelayMessage(null, null, null, null, null, null);

    private RelayMessageId id;
    private DestinationId destinationId;
    private MessagePayload messagePayload;
    private LocalDateTime createAt;
    private LocalDateTime reliedAt;
    private PayloadVersion payloadVersion;

    public static TopRelayMessage createByMessage(RelayMessage message) {
        RelayMessageId id = message.getId();
        MessagePayload messagePayload = message.getMessagePayload();
        DestinationId destinationId = message.getDestinationId();
        LocalDateTime reliedAt = message.getReliedAt();
        LocalDateTime createAt = message.getCreateAt();
        PayloadVersion payloadVersion = message.getPayloadVersion();

        return new TopRelayMessage(id, destinationId, messagePayload, createAt, reliedAt, payloadVersion);
    }
}

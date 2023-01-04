package org.masil.seoulyeok.events.relaymessage;

import com.masil.commons.clocks.Clocks;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.masil.seoulyeok.events.destination.DestinationId;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class RelayMessage {

    private RelayMessageId id;
    private final DestinationId destinationId;
    private final MessagePayload messagePayload;

    private final LocalDateTime createAt;
    private LocalDateTime reliedAt;
    private final PayloadVersion payloadVersion;

    public RelayMessage(DestinationId destinationId) {
        Long longId = 1L;
        this.id = RelayMessageId.of(longId);
        this.destinationId = destinationId;
        this.messagePayload = MessagePayload.of("");
        this.createAt = Clocks.now();
        this.payloadVersion = PayloadVersion.ZERO;
    }

    private RelayMessage(DestinationId destinationId, MessagePayload messagePayload, PayloadVersion payloadVersion) {
        this.id = RelayMessageId.of(1L);
        this.destinationId = destinationId;
        this.messagePayload = messagePayload;
        this.createAt = Clocks.now();
        this.payloadVersion = payloadVersion;
    }

    public RelayMessage(RelayMessageId id, DestinationId destinationId, MessagePayload messagePayload, PayloadVersion payloadVersion) {
        this.id = id;
        this.destinationId = destinationId;
        this.messagePayload = messagePayload;
        this.createAt = Clocks.now();
        this.payloadVersion = payloadVersion;
    }

    public static RelayMessage create(MessagePayload messagePayload, DestinationId destinationId, PayloadVersion payloadVersion) {
        return new RelayMessage(destinationId, messagePayload, payloadVersion);
    }
}

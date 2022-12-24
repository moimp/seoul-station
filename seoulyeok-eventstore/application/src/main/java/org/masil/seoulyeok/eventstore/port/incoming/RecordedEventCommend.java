package org.masil.seoulyeok.eventstore.port.incoming;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.masil.seoulyeok.common.SelfValidating;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = false)
public class RecordedEventCommend extends SelfValidating<RecordedEventCommend> {

    @NotNull
    Long id;
    @NotNull
    String eventType;
    @NotNull
    String payload;
    String version;
    LocalDateTime occurredAt;

    public RecordedEventCommend(Long id, String eventType, String payload, String version, LocalDateTime occurredAt) {
        this.id = id;
        this.eventType = eventType;
        this.payload = payload;
        this.occurredAt = occurredAt;
        this.version = version;
        validateSelf();
    }
}

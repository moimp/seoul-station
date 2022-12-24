package org.masil.seoulyeok.pulling;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.masil.seoulyeok.pulling.Status.*;
import static org.valid4j.Validation.validate;

@Getter(value = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class PulledOffset {

    private final PulledOffsetId id;
    private DestinationId destinationId;
    private final PipelineId pipeLineId;
    private final Position position;
    private LocalDateTime triggeredAt;
    private Status status;

    public static PulledOffset of(PulledOffsetId id, DestinationId destinationId, PipelineId pipelineId,
                                  Position position, LocalDateTime triggeredAt, Status status) {
        validate(!Objects.isNull(id), IllegalArgumentException.class);
        validate(!Objects.isNull(destinationId), IllegalArgumentException.class);
        validate(!Objects.isNull(pipelineId), IllegalArgumentException.class);
        validate(!Objects.isNull(position), IllegalArgumentException.class);
        validate(!Objects.isNull(triggeredAt), IllegalArgumentException.class);

        return new PulledOffset(id, destinationId, pipelineId, position, triggeredAt, status);
    }

    public void start() {
        if (!this.status.equals(READY)) {
            throw new IllegalStateException();
        }
        status = ON_PROGRESS;
    }

    public void stop() {
        if (!(this.status.equals(READY) || this.status.equals(ON_PROGRESS))) {
            throw new IllegalStateException("이미 종료된 PulledOffset 입니다." + this.id);
        }
        status = EXIT;
    }

    public boolean isOnProcessed() {
        return status == ON_PROGRESS;
    }

    public boolean isEnded() {
        return status == EXIT;
    }

    public boolean isReady() {
        return status == READY;
    }

    public PipelineId pipelineId() {
        return pipeLineId;

    }

    public Position currentPosition() {
        return position;
    }

    public Status status() {
        return status;
    }

    public PulledOffsetId id() {
        return id;

    }

    public DestinationId destinationId() {
        return destinationId;
    }

    public Position getTheLatestPositionBetween(LocalDateTime target) {
        return position.getTheLatestPositionBetween(target);

    }

    public LocalDateTime triggerAt() {
        return triggeredAt;
    }
}

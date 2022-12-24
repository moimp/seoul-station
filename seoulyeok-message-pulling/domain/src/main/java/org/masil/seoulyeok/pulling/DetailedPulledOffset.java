package org.masil.seoulyeok.pulling;

import com.masil.commons.clocks.Clocks;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.masil.seoulyeok.pulling.Status.*;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DetailedPulledOffset {

    private final PulledOffsetId pulledOffsetId;

    private Position position;

    private LocalDateTime triggeredAt;
    private final DestinationId destinationId;
    private final TargetEvents targetEvents;
    private final Size size;
    private Status status;

    public static DetailedPulledOffset of(PulledOffsetId pulledOffsetId, Position position, LocalDateTime triggeredAt, DestinationId destinationId, TargetEvents targetEvents, Size size) {
        return new DetailedPulledOffset(pulledOffsetId, position, triggeredAt, destinationId, targetEvents, size, READY);
    }

    public static DetailedPulledOffset of(LocalDateTime position, LocalDateTime triggeredAt, DestinationId destinationId, TargetEvents targetEvents, Long size) {
        PulledOffsetId pulledOffsetId = PulledOffsetId.of(UUID.randomUUID().timestamp());

        return DetailedPulledOffset.of(pulledOffsetId, Position.of(position), triggeredAt, destinationId, targetEvents, Size.of(size));
    }

    public void updateUptoDate(Position position) {
        this.position = position;
        this.triggeredAt = Clocks.now();
    }

    public void start() {
        if (!this.status.equals(READY)) {
            throw new IllegalStateException();
        }
        status = ON_PROGRESS;
    }

    public void exit() {
        if (!(this.status.equals(READY) || this.status.equals(ON_PROGRESS))) {
            throw new IllegalStateException();
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
}

package org.masil.seoulyeok.pulling.incoming.persistence;

import com.masil.commons.clocks.Clocks;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.pulling.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceConstructor)
@EqualsAndHashCode
@Table("pulled_offset")
public class PulledOffsetJdbcEntity {

    @Id
    private Long id;

    @With
    private LocalDateTime currentPosition;
    @With
    private LocalDateTime triggeredAt;
    private Long pipelineId;
    private Long destinationId;
    @With
    private Status status;

    @CreatedDate
    private LocalDateTime createdAt;

    @Version
    private Long version;

    public static PulledOffsetJdbcEntity of(Long id, Long pipelineId, Long destinationId, LocalDateTime currentPosition) {
        return new PulledOffsetJdbcEntity(id, currentPosition, Clocks.now(), pipelineId, destinationId, Status.READY, Clocks.now(), null);
    }

    public static PulledOffsetJdbcEntity of(Long pipelineId, Long destinationId, LocalDateTime currentPosition) {
        return new PulledOffsetJdbcEntity(UUID.randomUUID().timestamp(), currentPosition, Clocks.now(), pipelineId, destinationId, Status.READY, Clocks.now(), null);
    }
}

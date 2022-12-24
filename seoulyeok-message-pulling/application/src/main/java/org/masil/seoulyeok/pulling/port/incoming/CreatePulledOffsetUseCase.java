package org.masil.seoulyeok.pulling.port.incoming;

import java.time.LocalDateTime;

public interface CreatePulledOffsetUseCase {
    void create(Long pipelineId, Long destinationId, LocalDateTime startingPointAt);
}

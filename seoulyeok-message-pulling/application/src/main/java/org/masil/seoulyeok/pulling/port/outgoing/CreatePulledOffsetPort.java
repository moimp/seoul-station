package org.masil.seoulyeok.pulling.port.outgoing;

import java.time.LocalDateTime;

public interface CreatePulledOffsetPort {

    void create(Long pipelineId, Long destinationId, LocalDateTime startingPointAt);

}

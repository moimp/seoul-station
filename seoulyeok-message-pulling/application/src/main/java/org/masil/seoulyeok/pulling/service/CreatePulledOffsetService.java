package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.port.incoming.CreatePulledOffsetUseCase;
import org.masil.seoulyeok.pulling.port.outgoing.CreatePulledOffsetPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreatePulledOffsetService implements CreatePulledOffsetUseCase {

    private final CreatePulledOffsetPort createPulledOffsetPort;

    @Override
    public void create(Long pipelineId, Long destinationId, LocalDateTime startingPointAt) {
        createPulledOffsetPort.create(pipelineId, destinationId, startingPointAt);
    }
}

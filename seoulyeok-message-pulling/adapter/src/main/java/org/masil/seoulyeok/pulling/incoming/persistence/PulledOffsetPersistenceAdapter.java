package org.masil.seoulyeok.pulling.incoming.persistence;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.*;
import org.masil.seoulyeok.pulling.port.outgoing.CreatePulledOffsetPort;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPulledOffsetPort;
import org.masil.seoulyeok.pulling.port.outgoing.UpdatePulledOffsetPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.valid4j.Validation.validate;

@Service
@RequiredArgsConstructor
public class PulledOffsetPersistenceAdapter implements CreatePulledOffsetPort, LoadPulledOffsetPort, UpdatePulledOffsetPort {

    private final SpringDataPulledOffsetRepository repository;

    @Override
    @Transactional
    public void create(Long pipelineId, Long destinationId, LocalDateTime startingPointAt) {
        validate(!Objects.isNull(pipelineId), IllegalArgumentException.class);
        validate(!Objects.isNull(startingPointAt), IllegalArgumentException.class);
        validate(!Objects.isNull(destinationId), IllegalArgumentException.class);

        PulledOffsetJdbcEntity createdPulledOffset = PulledOffsetJdbcEntity.of(pipelineId, destinationId, startingPointAt);
        repository.save(createdPulledOffset);
    }

    @Override
    public List<PulledOffset> findAllOnProgressedPulledOffset() {
        List<PulledOffsetJdbcEntity> allOnProgressedPulledOffset = repository.findAllOnProgressedPulledOffset();
        return allOnProgressedPulledOffset.stream()
                .map(this::convertEntityToDomain).collect(Collectors.toList());
    }

    @Override
    public PulledOffset findByPipelineId(PipelineId pipelineId) {
        PulledOffsetJdbcEntity pulledOffsetJdbcEntity = repository.findByPipelineId(pipelineId.getValue());
        return convertEntityToDomain(pulledOffsetJdbcEntity);
    }

    @Override
    @Transactional
    public void updatePulledOffsetEntityByCurrentOffset(DetailedPulledOffset detailedPulledOffset) {
        PulledOffsetId pulledOffsetId = detailedPulledOffset.getPulledOffsetId();
        PulledOffsetJdbcEntity pulledOffsetJdbcEntity = repository.findById(pulledOffsetId.getId()).orElseThrow();

        PulledOffsetJdbcEntity updated = pulledOffsetJdbcEntity
                .withCurrentPosition(detailedPulledOffset.getPosition().getValue())
                .withTriggeredAt(detailedPulledOffset.getTriggeredAt());
        repository.save(updated);
    }

    @Override
    public void updatePulledOffset(PulledOffset pulledOffset) {
        PulledOffsetId pulledOffsetId = pulledOffset.id();
        PulledOffsetJdbcEntity pulledOffsetJdbcEntity = repository.findById(pulledOffsetId.getId()).orElseThrow();

        PulledOffsetJdbcEntity updated = pulledOffsetJdbcEntity
                .withCurrentPosition(pulledOffset.currentPosition().getValue())
                .withStatus(pulledOffset.status())
                .withTriggeredAt(pulledOffset.triggerAt());
        repository.save(updated);
    }

    private PulledOffset convertEntityToDomain(PulledOffsetJdbcEntity pulledOffsetJdbc) {
        return PulledOffset.of(
                PulledOffsetId.of(pulledOffsetJdbc.getId()),
                DestinationId.of(pulledOffsetJdbc.getDestinationId()),
                PipelineId.of(pulledOffsetJdbc.getPipelineId()),
                Position.of(pulledOffsetJdbc.getCurrentPosition()),
                pulledOffsetJdbc.getTriggeredAt(),
                pulledOffsetJdbc.getStatus()
        );
    }
}

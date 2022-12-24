package org.masil.seoulyeok.pulling.incoming.web;

import lombok.Data;
import org.masil.seoulyeok.pulling.port.incoming.CreatePulledOffsetUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "apis/events",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        params = {"v=0.1.0"})
public class CreateOffsetController {

    private final CreatePulledOffsetUseCase createPulledOffsetUseCase;

    public CreateOffsetController(CreatePulledOffsetUseCase createPulledOffsetUseCase) {
        this.createPulledOffsetUseCase = createPulledOffsetUseCase;
    }

    @PostMapping(value = "/puller")
    public ResponseEntity<Result> createOffset(@Valid @RequestBody PullingRequest pullingRequest) {

        Long pipelineId = pullingRequest.getPipelineId();
        LocalDateTime startingPointAt = pullingRequest.getStartingPointAt();
        Long destinationId = pullingRequest.getDestinationId();


        createPulledOffsetUseCase.create(pipelineId,destinationId, startingPointAt);
        return ResponseEntity.ok(new Result(1, "ok"));
    }

    @Data
    private static class PullingRequest {

        @NotNull
        private final Long pipelineId;
        @NotNull
        private final Long destinationId;
        @NotNull
        private final LocalDateTime startingPointAt;
    }

    @Data
    private static class Result {

        @NotNull
        private final int code;
        @NotNull
        private final String message;

    }
}

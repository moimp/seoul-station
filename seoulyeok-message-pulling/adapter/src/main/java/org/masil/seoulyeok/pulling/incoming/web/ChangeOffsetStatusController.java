package org.masil.seoulyeok.pulling.incoming.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.port.incoming.ChangePulledOffsetStatusUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "apis/events",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        params = {"v=0.1.0"})
@RequiredArgsConstructor
public class ChangeOffsetStatusController {

    private final ChangePulledOffsetStatusUseCase changePulledOffsetStatusUseCase;


    @PatchMapping("/pullers")
    public ResponseEntity<Result> changeStatus(@Valid @RequestBody ChangeStatusRequest request) {
        changePulledOffsetStatusUseCase.changeStatus(request.getPipelineId(), request.getState());
        return ResponseEntity.ok(new Result(1, "ok"));
    }
    @Data
    private static class ChangeStatusRequest {
        private final Long pipelineId;
        private final String state;
    }

    @Data
    private static class Result {

        @NotNull
        private final int code;
        @NotNull
        private final String message;

    }
}

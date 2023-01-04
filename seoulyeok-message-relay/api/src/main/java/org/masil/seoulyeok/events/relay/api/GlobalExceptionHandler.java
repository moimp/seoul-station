package org.masil.seoulyeok.events.relay.api;

import org.masil.seoulyeok.events.relay.models.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.internalServerError()
                .body(new Result()
                        .code(-1)
                        .message(e.getMessage())
                );
    }
}

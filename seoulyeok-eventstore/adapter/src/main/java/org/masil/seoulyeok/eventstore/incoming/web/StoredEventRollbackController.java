package org.masil.seoulyeok.eventstore.incoming.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.WebAdapter;
import org.masil.seoulyeok.eventstore.DomainEventId;
import org.masil.seoulyeok.eventstore.port.incoming.StoredEventRollbackCommand;
import org.masil.seoulyeok.eventstore.port.incoming.StoredEventRollbackUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class StoredEventRollbackController {

    private final StoredEventRollbackUseCase rollbackUseCase;

    @PutMapping(value = "/apis/events")
    public ResponseEntity<RollbackResponse> storeEvent(@RequestBody RollbackRequest request) {
        List<DomainEventId> rollbackFailedIds = rollbackUseCase.rollback(convertToCommand(request));
        if (rollbackFailedIds.size() != 0) {
            return ResponseEntity.internalServerError().body(convertToFailedResponse(rollbackFailedIds));
        }
        return ResponseEntity.ok(new RollbackResponse<>("success", "롤백에 성공하였습니다"));
    }

    private StoredEventRollbackCommand convertToCommand(RollbackRequest request) {
        List<DomainEventId> domainEventIds = request.getEventIds().stream()
                .map(DomainEventId::of)
                .collect(Collectors.toList());
        return StoredEventRollbackCommand.of(domainEventIds);
    }

    private RollbackResponse<List<Long>> convertToFailedResponse(List<DomainEventId> failedIds) {
        List<Long> ids = failedIds.stream().map(DomainEventId::get).collect(Collectors.toList());
        return new RollbackResponse<>("fail", ids);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RollbackRequest {
        private List<Long> eventIds;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RollbackResponse<T> {
        private String code;
        private T msg;
    }
}

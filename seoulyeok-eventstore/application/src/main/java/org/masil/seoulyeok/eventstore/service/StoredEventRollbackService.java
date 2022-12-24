package org.masil.seoulyeok.eventstore.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.UseCase;
import org.masil.seoulyeok.eventstore.DomainEventId;
import org.masil.seoulyeok.eventstore.port.incoming.StoredEventRollbackCommand;
import org.masil.seoulyeok.eventstore.port.incoming.StoredEventRollbackUseCase;
import org.masil.seoulyeok.eventstore.port.outgoing.DeleteEventPort;
import org.masil.seoulyeok.eventstore.port.outgoing.LoadEventPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class StoredEventRollbackService implements StoredEventRollbackUseCase {

    private final LoadEventPort loadEventPort;
    private final DeleteEventPort deleteEventPort;

    @Override
    public List<DomainEventId> rollback(StoredEventRollbackCommand command) {
        return this.rollbackAndCollectFailedIds(command.getDomainEventIds());
    }

    private List<DomainEventId> rollbackAndCollectFailedIds(List<DomainEventId> rollbackTargetIds) {
        List<DomainEventId> failedIds = new ArrayList<>();
        for (DomainEventId id : rollbackTargetIds) {
            if (isInValidCommand(id)) {
                failedIds.add(id);
                continue;
            }
            deleteEventPort.delete(id);
        }
        return failedIds;
    }

    private boolean isInValidCommand(DomainEventId id) {
        boolean valid = loadEventPort.existsEvent(id.get());
        return !valid;
    }
}

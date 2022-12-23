package org.masil.seoulyeok.eventstore.port.incoming;

import org.masil.seoulyeok.eventstore.DomainEventId;

import java.util.List;

public interface StoredEventRollbackUseCase {
    List<DomainEventId> rollback(StoredEventRollbackCommand command);
}

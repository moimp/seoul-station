package org.masil.seoulyeok.eventstore.port.incoming;

import lombok.Value;
import org.masil.seoulyeok.eventstore.DomainEventId;

import java.util.List;

@Value(staticConstructor = "of")
public class StoredEventRollbackCommand {
    List<DomainEventId> domainEventIds;
}

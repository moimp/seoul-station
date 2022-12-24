package org.masil.seoulyeok.eventstore.port.outgoing;

import org.masil.seoulyeok.eventstore.DomainEventId;

public interface DeleteEventPort {
    void delete(DomainEventId eventId);
}

package org.masil.seoulyeok.eventstore.port.incoming;

import org.masil.seoulyeok.eventstore.Event;

public interface GetEventUseCase {
    Event query(GetEventCommand command);
}

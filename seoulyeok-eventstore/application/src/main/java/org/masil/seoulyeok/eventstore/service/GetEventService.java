package org.masil.seoulyeok.eventstore.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.common.UseCase;
import org.masil.seoulyeok.eventstore.Event;
import org.masil.seoulyeok.eventstore.port.incoming.GetEventCommand;
import org.masil.seoulyeok.eventstore.port.incoming.GetEventUseCase;
import org.masil.seoulyeok.eventstore.port.outgoing.LoadEventPort;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class GetEventService implements GetEventUseCase {

    private final LoadEventPort loadEventPort;

    @Override
    public Event query(GetEventCommand command) {
        return loadEventPort.loadEvent(command.getEventId());
    }
}

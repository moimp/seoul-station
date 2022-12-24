package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.pulling.DetailedPulledOffset;
import org.masil.seoulyeok.pulling.port.outgoing.LoadEventsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NextEventsSelector {

    private final LoadEventsPort loadEventsPort;

    @Transactional(readOnly = true)
    public List<Event> getNextEvents(DetailedPulledOffset detailedPulledOffset) {
        List<Event> nextEventsByPulledOffset = loadEventsPort.findNextEventsByPulledOffset(detailedPulledOffset);

        nextEventsByPulledOffset.sort(Comparator.comparing(Event::getOccurredAt));
        return nextEventsByPulledOffset;
    }
}

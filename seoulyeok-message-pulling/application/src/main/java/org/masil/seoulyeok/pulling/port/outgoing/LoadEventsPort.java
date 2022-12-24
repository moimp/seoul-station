package org.masil.seoulyeok.pulling.port.outgoing;


import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.pulling.DetailedPulledOffset;

import java.util.List;

public interface LoadEventsPort {

    List<Event> findNextEventsByPulledOffset(DetailedPulledOffset detailedPulledOffset);
}

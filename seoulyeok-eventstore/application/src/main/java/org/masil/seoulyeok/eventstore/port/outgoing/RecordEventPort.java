package org.masil.seoulyeok.eventstore.port.outgoing;


import org.masil.seoulyeok.eventstore.Event;

import java.util.List;

public interface RecordEventPort {
    void record(Event event);

    void record(List<Event> events);
}

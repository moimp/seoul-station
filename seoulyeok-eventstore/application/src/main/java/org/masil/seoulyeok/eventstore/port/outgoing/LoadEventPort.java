package org.masil.seoulyeok.eventstore.port.outgoing;


import org.masil.seoulyeok.eventstore.Event;

public interface LoadEventPort {

    Event loadEvent(Long messageId);
    boolean existsEvent(Long messageId);
}

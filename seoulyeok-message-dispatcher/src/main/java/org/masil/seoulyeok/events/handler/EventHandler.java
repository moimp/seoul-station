package org.masil.seoulyeok.events.handler;

import org.masil.seoulyeok.events.core.Event;

public interface EventHandler {

    void handle(Event event);

    boolean isSupport(Event event);
}

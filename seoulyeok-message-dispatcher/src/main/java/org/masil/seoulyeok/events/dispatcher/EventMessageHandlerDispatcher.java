package org.masil.seoulyeok.events.dispatcher;

import org.masil.seoulyeok.events.core.Event;
import org.masil.seoulyeok.events.handler.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EventMessageHandlerDispatcher {

    List<EventHandler> handlers = new ArrayList<>();

    public void doDispatcher(Event event) {
        EventHandler handler;
        try {
            handler = handlers.stream().filter(h -> h.isSupport(event)).findFirst().get();
            handler.handle(event);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Not Found handler");
        }
    }

    public void register(EventHandler eventHandler) {
        handlers.add(eventHandler);
    }
}

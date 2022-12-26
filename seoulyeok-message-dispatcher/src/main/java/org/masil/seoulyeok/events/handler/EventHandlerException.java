package org.masil.seoulyeok.events.handler;

public class EventHandlerException extends RuntimeException {
    public EventHandlerException(String message) {
        super(message);
    }

    public static EventHandlerException withMessage(String message) {
        return new EventHandlerException(message);
    }
}

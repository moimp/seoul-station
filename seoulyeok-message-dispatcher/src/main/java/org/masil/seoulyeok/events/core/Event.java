package org.masil.seoulyeok.events.core;

public interface Event {

    EventType getType();

    EventVersion getVersion();
}

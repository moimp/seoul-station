package org.masil.seoulyeok.events.core;

public abstract class AbstractEvent implements Event {
    private final EventType type;
    private final EventVersion version;

    public AbstractEvent(EventType type, EventVersion version) {
        this.type = type;
        this.version = version;
    }

    public AbstractEvent(String type, String version) {
        this(EventType.of(type), EventVersion.of(version));
    }

    @Override
    public EventType getType() {
        return this.type;
    }

    @Override
    public EventVersion getVersion() {
        return this.version;
    }
}

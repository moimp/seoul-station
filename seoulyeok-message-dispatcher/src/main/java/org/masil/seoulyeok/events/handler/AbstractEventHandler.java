package org.masil.seoulyeok.events.handler;

import static org.valid4j.Validation.validate;

import org.masil.commons.condition.Condition;
import org.masil.seoulyeok.events.command.Command;
import org.masil.seoulyeok.events.core.Event;

abstract class AbstractEventHandler<C extends Command> implements EventHandler {

    Condition<Event> supportSpec;

    public AbstractEventHandler(Condition<Event> supportSpec) {
        this.supportSpec = supportSpec;
    }

    @Override
    public void handle(Event event) {
        validate(event != null, new EventHandlerException("event must be not null"));
        validate(isSupport(event), new EventHandlerException("Not Support this Event"));

        C command = convert(event);

        handle(command);
    }

    protected abstract void handle(C command);

    protected abstract C convert(Event event);

    @Override
    public boolean isSupport(Event event) {
        return supportSpec.isSatisfy(event);
    }
}

package org.masil.seoulyeok.events.handler;

import org.masil.commons.condition.Condition;
import org.masil.seoulyeok.events.command.Command;
import org.masil.seoulyeok.events.command.CommandHandler;
import org.masil.seoulyeok.events.command.EventToCommandConverter;
import org.masil.seoulyeok.events.core.Event;
import org.masil.seoulyeok.events.core.EventType;
import org.masil.seoulyeok.events.core.EventVersion;

class GeneralEventHandler<C extends Command> extends AbstractEventHandler<C> {

    private final EventToCommandConverter<C> eventToCommandConverter;
    private final CommandHandler<C> commandHandler;

    GeneralEventHandler(
            EventType eventType,
            EventVersion eventVersion,
            EventToCommandConverter<C> eventToCommandConverter,
            CommandHandler<C> commandHandler) {
        this(EventTypeCondition.of(eventType)
                        .and(EventVersionCondition.of(eventVersion)),
                eventToCommandConverter,
                commandHandler);
    }

    GeneralEventHandler(
            Condition<Event> supportSpec,
            EventToCommandConverter<C> eventToCommandConverter,
            CommandHandler<C> commandHandler) {
        super(supportSpec);
        this.commandHandler = commandHandler;
        this.eventToCommandConverter = eventToCommandConverter;
    }

    @Override
    protected void handle(C command) {
        commandHandler.doCommand(command);
    }

    @Override
    protected C convert(Event event) {
        return eventToCommandConverter.convert(event);
    }
}

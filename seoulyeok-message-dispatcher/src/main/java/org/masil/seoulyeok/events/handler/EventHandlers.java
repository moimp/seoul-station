package org.masil.seoulyeok.events.handler;

import org.masil.seoulyeok.events.command.Command;
import org.masil.seoulyeok.events.command.CommandHandler;
import org.masil.seoulyeok.events.command.EventToCommandConverter;
import org.masil.seoulyeok.events.core.EventType;
import org.masil.seoulyeok.events.core.EventVersion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventHandlers {

    public static <C extends Command> EventHandler initHandler(
            String eventType, String eventValue,
            EventToCommandConverter<C> factory,
            CommandHandler<C> commandHandler) {
        return initHandler(EventType.of(eventType), EventVersion.of(eventValue), factory, commandHandler);
    }

    public static <C extends Command> EventHandler initHandler(
            EventType eventType, EventVersion eventValue,
            EventToCommandConverter<C> factory,
            CommandHandler<C> commandHandler) {
        return new GeneralEventHandler<>(
                eventType,
                eventValue,
                factory,
                commandHandler);
    }

}

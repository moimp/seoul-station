package org.masil.seoulyeok.events.command;

import org.masil.seoulyeok.events.core.Event;

public interface EventToCommandConverter<C extends Command> {

    C convert(Event anEvent);

}

package org.masil.seoulyeok.events.command;

public interface CommandHandler<C extends Command> {


    void doCommand(C aCommand);
}

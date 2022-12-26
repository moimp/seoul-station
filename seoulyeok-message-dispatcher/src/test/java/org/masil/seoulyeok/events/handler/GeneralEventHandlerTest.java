package org.masil.seoulyeok.events.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.masil.seoulyeok.events.command.Command;
import org.masil.seoulyeok.events.command.CommandHandler;
import org.masil.seoulyeok.events.core.AbstractEvent;
import org.masil.seoulyeok.events.core.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneralEventHandlerTest {

    public static class FooEvent extends AbstractEvent {
        public FooEvent() {
            super("foo", "v1");
        }
    }

    public static class FooCommand implements Command {
    }

    Event fooEvent = new FooEvent();
    FooCommand fooCommand = new FooCommand();

    @Mock
    CommandHandler<FooCommand> commandHandler;

    @Test
    void handle() {

        EventHandler sut = EventHandlers.initHandler("foo", "v1", anEvent -> fooCommand, commandHandler);

        sut.handle(fooEvent);

        ArgumentCaptor<FooCommand> capture = ArgumentCaptor.forClass(FooCommand.class);
        verify(commandHandler).doCommand(capture.capture());
        assertThat(capture.getValue()).isInstanceOf(FooCommand.class);

    }
}

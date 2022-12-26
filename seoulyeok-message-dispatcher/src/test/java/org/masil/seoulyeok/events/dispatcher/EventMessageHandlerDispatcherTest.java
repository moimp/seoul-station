package org.masil.seoulyeok.events.dispatcher;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.masil.seoulyeok.events.core.AbstractEvent;
import org.masil.seoulyeok.events.core.Event;
import org.masil.seoulyeok.events.handler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventMessageHandlerDispatcherTest {

    private static final String SOME_EVENT = "someEvent";

    @Mock
    EventHandler eventHandler;

    Event event = new AbstractEvent(SOME_EVENT, "v1") {};

    EventMessageHandlerDispatcher sut;

    @Test
    void doDispatcher() {
        sut = new EventMessageHandlerDispatcher();
        given(eventHandler.isSupport(event)).willReturn(true);
        sut.register(eventHandler);

        sut.doDispatcher(event);

        verify(eventHandler).handle(event);
    }

    @Test
    void when_not_found_dispatcher_throw_exception() {
        sut = new EventMessageHandlerDispatcher();
        given(eventHandler.isSupport(event)).willReturn(false);
        sut.register(eventHandler);

        assertThatThrownBy(() ->
                sut.doDispatcher(event))
                .hasMessage("Not Found handler");
        verify(eventHandler, never()).handle(event);
    }
}

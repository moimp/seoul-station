package org.masil.seoulyeok.pulling.port.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.events.EventId;
import org.masil.seoulyeok.pulling.*;
import org.masil.seoulyeok.pulling.port.outgoing.LoadEventsPort;
import org.masil.seoulyeok.pulling.service.NextEventsSelector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NextEventsSelectorTest {

    @InjectMocks
    NextEventsSelector sut;

    @Mock
    LoadEventsPort loadEventsPort;

    DetailedPulledOffset detailedPulledOffset;

    @BeforeEach
    void setUp() {
        Position position = Position.of(LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        Size size = Size.of(100);

        detailedPulledOffset = DetailedPulledOffset.of(PulledOffsetId.of(1L), position, null, DestinationId.of(1L), TargetEvents.of(new ArrayList<>()), size);

    }

    @Test
    void 다음_이벤트를_반환한다() {
        List<Event> value = new ArrayList<>();
        given(loadEventsPort.findNextEventsByPulledOffset(detailedPulledOffset)).willReturn(value);

        List<Event> nextEvents = sut.getNextEvents(detailedPulledOffset);

        assertThat(nextEvents).isEqualTo(value);
    }

    @Test
    void 정렬된_순서의_이벤트_를_반환한다() {
        List<Event> foundEvents = new ArrayList<>();
        LocalDateTime occurredAt = LocalDateTime.of(2022, 1, 1, 1, 1, 1, 1);

        Event _3_event = Event.of(EventId.of(3L), "a", "1", "1", occurredAt.plusNanos(2));
        Event _2_event = Event.of(EventId.of(2L), "a", "1", "1", occurredAt.plusNanos(1));
        Event _1_event = Event.of(EventId.of(1L), "b", "1", "1", occurredAt);

        foundEvents.add(_3_event);
        foundEvents.add(_2_event);
        foundEvents.add(_1_event);

        given(loadEventsPort.findNextEventsByPulledOffset(detailedPulledOffset)).willReturn(foundEvents);

        List<Event> nextEvents = sut.getNextEvents(detailedPulledOffset);

        assertThat(nextEvents).containsExactly(
                _1_event,
                _2_event,
                _3_event
        );
    }
}

package org.masil.seoulyeok.pulling.port.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.Event;
import org.masil.seoulyeok.events.EventId;
import org.masil.seoulyeok.pulling.*;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPipelineEventsPort;
import org.masil.seoulyeok.pulling.service.*;
import org.masil.seoulyeok.ref.pipeline.PipelineEvent;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SimpleEventsProcessorTest {

    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    private final PulledOffsetId ANY_PULLED_OFFSET_ID = PulledOffsetId.of(1L);
    private final PipelineId ANY_PIPELINE_ID = PipelineId.of(1L);

    private final LocalDateTime ANY_DATE_TIME = LocalDateTime.of(2022, 1, 1, 1, 1, 1);
    private final Position ANY_POSITION = Position.of(ANY_DATE_TIME);
    private final Size ANY_SIZE = Size.of(100);
    private final DestinationId destinationId = DestinationId.of(1L);
    private final PipelineEvent ANY_PIPELINE_EVENT = new PipelineEvent(new ArrayList<>(), ANY_DATE_TIME, ANY_SIZE.getValue());
    private final PulledOffset ANY_PULLED_OFFSET = new PulledOffset(ANY_PULLED_OFFSET_ID, ANY_DESTINATION_ID, ANY_PIPELINE_ID, ANY_POSITION, ANY_DATE_TIME, Status.ON_PROGRESS);

    @InjectMocks
    SimpleEventsProcessor sut;

    @Mock
    PulledOffsetFinder pulledOffsetFinder;

    @Mock
    NextEventsSelector nextEventsSelector;

    @Mock
    EventsMessageToRelaySender eventsMessageToRelaySender;

    @Mock
    PulledOffsetUpdater pulledOffsetUpdater;

    @Mock
    LoadPipelineEventsPort loadPipelineEventsPort;

    @Test
    void noActivePulledOffset() {

        given(pulledOffsetFinder.findAllOnProgressPulledOffset()).willReturn(List.of());
        sut.next();

        verify(pulledOffsetFinder).findAllOnProgressPulledOffset();
        verifyNoInteractions(nextEventsSelector);
        verifyNoInteractions(eventsMessageToRelaySender);
        verifyNoInteractions(pulledOffsetUpdater);
        verifyNoInteractions(loadPipelineEventsPort);
    }

    @Test
    void nextTest() {

        given(pulledOffsetFinder.findAllOnProgressPulledOffset()).willReturn(List.of(ANY_PULLED_OFFSET));

        List<Event> events = new ArrayList<>();
        events.add(Event.of(EventId.of(1L), "a", "a", "a", LocalDateTime.now()));

        given(nextEventsSelector.getNextEvents(any(DetailedPulledOffset.class))).willReturn(events);
        given(loadPipelineEventsPort.get(ANY_PIPELINE_ID)).willReturn(ANY_PIPELINE_EVENT);

        sut.next();
        InOrder inOrder = inOrder(pulledOffsetFinder,
                nextEventsSelector,
                eventsMessageToRelaySender,
                pulledOffsetUpdater);

        inOrder.verify(pulledOffsetFinder).findAllOnProgressPulledOffset();
        inOrder.verify(nextEventsSelector).getNextEvents(any(DetailedPulledOffset.class));
        inOrder.verify(eventsMessageToRelaySender).send(destinationId, events);
        inOrder.verify(pulledOffsetUpdater).update(any(DetailedPulledOffset.class));
    }
}

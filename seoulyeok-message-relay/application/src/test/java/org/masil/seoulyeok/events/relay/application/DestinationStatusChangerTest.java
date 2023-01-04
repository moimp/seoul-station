package org.masil.seoulyeok.events.relay.application;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.relay.port.out.CommandDestinationPort;
import org.masil.seoulyeok.events.relay.port.out.LoadDestinationPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DestinationStatusChangerTest {
    public static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    public static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, Address.of("some address"),
            DestinationType.SIMPLE_QUEUE);

    @InjectMocks
    DestinationStatusChanger sut;

    @Mock
    LoadDestinationPort loadPort;

    @Mock
    CommandDestinationPort commandPort;

    @Test
    void inactive_를_요청하면_조회후_status_를_변경하고_저장한다() {
        given(loadPort.loadById(ANY_DESTINATION_ID)).willReturn(ANY_DESTINATION);

        sut.inactivateBy(ANY_DESTINATION_ID);

        verify(commandPort).update(ANY_DESTINATION);
    }
}

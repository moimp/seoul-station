package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.relay.port.out.QueryRelayMessagePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class TopMessageFinderTest {

    @InjectMocks
    TopMessageFinder sut;

    @Mock
    QueryRelayMessagePort port;

    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    private static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, Address.of("XX"), DestinationType.SIMPLE_QUEUE);

    @Test
    void name() {
        given(port.existsByDestinationId(ANY_DESTINATION_ID)).willReturn(true);


        sut.find(ANY_DESTINATION_ID);
    }
}

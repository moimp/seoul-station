package org.masil.seoulyeok.events.relay.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.relay.port.out.LoadDestinationPort;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DestinationFetcherTest {

    @InjectMocks
    DestinationFetcher sut;

    @Mock
    LoadDestinationPort loadPort;

    @Test
    void Destination_을_조회하면_active_상태의_destination_을_가져온다() {
        List<Destination> destinations = new ArrayList<>();
        given(loadPort.loadAllActiveDestination()).willReturn(destinations);

        List<Destination> actual = sut.findAllActiveDestination();

        assertThat(actual).isEqualTo(actual);
    }
}

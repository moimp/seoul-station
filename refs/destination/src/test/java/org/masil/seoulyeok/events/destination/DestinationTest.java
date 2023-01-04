package org.masil.seoulyeok.events.destination;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.masil.seoulyeok.events.destination.*;

class DestinationTest {

    Destination sut;

    @Test
    void inactive_상태로_갈_수_있다() {
        sut = Destination.of(DestinationId.of(1L), Address.of("some_address"), DestinationType.SIMPLE_QUEUE);
        assertThat(sut.getStatus()).isEqualTo(DestinationStatus.ACTIVE);

        sut.inactive();
        assertThat(sut.getStatus()).isEqualTo(DestinationStatus.INACTIVE);
    }
}

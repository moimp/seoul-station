package org.masil.seoulyeok.eventstore;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventTest {

    @Test
    void create() {
        Event event = Event.of(DomainEventId.of(1L), "type", "", "", LocalDateTime.now());
        assertThat(event).isNotNull();
    }

    @Test
    void validate() {
        assertThatThrownBy(() -> Event.of(null, "type", "", "", LocalDateTime.now())).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Event.of(DomainEventId.of(1L), null, "", "", LocalDateTime.now())).isInstanceOf(NullPointerException.class);
    }
}

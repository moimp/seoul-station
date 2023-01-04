package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.relay.port.out.RelayMessageMarkPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RelaidMessageUpdaterTest {

    private static final LocalDateTime ANY_PUBLISHED_AT = LocalDateTime.of(2022, 1, 1, 1, 1);
    private static final RelayMessageId ANY_RELAY_MESSAGE_ID = RelayMessageId.of(1L);
    @InjectMocks
    RelaidMessageUpdater sut;

    @Mock
    RelayMessageMarkPort port;

    @Test
    void markTest() {

        sut.setMarkRelaidMessage(ANY_RELAY_MESSAGE_ID, ANY_PUBLISHED_AT);

        verify(port).setReliedMark(ANY_RELAY_MESSAGE_ID, ANY_PUBLISHED_AT);
    }
}

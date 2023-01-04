package org.masil.seoulyeok.events.relay.application;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchedulerClientTest {

    @InjectMocks
    SchedulerClient sut;

    @Mock
    RelayProcessor relayProcessor;

    @Test
    void execute() {
        sut.execute();

        verify(relayProcessor).doProcess();
    }
}

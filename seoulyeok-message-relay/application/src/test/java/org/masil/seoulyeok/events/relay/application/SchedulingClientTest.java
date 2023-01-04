package org.masil.seoulyeok.events.relay.application;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import org.masil.seoulyeok.events.relay.application.config.SchedulerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(SchedulerConfig.class)
class SchedulingClientTest {

    @SpyBean
    SchedulerClient sut;

    @MockBean
    RelayProcessor processor;

    @Test
    void schedulingTest() {
        await()
                .atMost(Duration.ofSeconds(1L))
                .untilAsserted(() -> verify(sut, atLeast(1)).execute());
    }
}

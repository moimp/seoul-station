package org.masil.seoulyeok.events.relay.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerClient {
    private final RelayProcessor processor;

    @Scheduled(fixedDelay = 5000)
    public void execute() {

        processor.doProcess();
    }
}

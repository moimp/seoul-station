package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerClient {
    private final PullingEventsExecutor pullingEventsExecutor;

    @Scheduled(fixedDelay = 5000L)
    public void runWithScheduled(){
        pullingEventsExecutor.execute();
    }
}

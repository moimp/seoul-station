package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PullingEventsExecutor {

    private final SimpleEventsProcessor simpleEventsProcessor;

    public void execute() {
        simpleEventsProcessor.next();
    }
}

package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.DetailedPulledOffset;
import org.masil.seoulyeok.pulling.PulledOffset;
import org.masil.seoulyeok.pulling.port.outgoing.UpdatePulledOffsetPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PulledOffsetUpdater {

    private final UpdatePulledOffsetPort updatePulledOffsetPort;

    public void update(DetailedPulledOffset detailedPulledOffset) {
        updatePulledOffsetPort.updatePulledOffsetEntityByCurrentOffset(detailedPulledOffset);
    }

    public void update(PulledOffset pulledOffset) {
        updatePulledOffsetPort.updatePulledOffset(pulledOffset);
    }
}

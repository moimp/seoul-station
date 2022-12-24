package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.pulling.PulledOffset;
import org.masil.seoulyeok.pulling.Status;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPulledOffsetPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PulledOffsetFinder {

    private final LoadPulledOffsetPort loadPulledOffsetPort;

    public List<PulledOffset> findAllOnProgressPulledOffset() {
        List<PulledOffset> pulledOffsets = loadPulledOffsetPort.findAllOnProgressedPulledOffset();

        if (validatePulledOffsetStatus(pulledOffsets)) {
            throw new IllegalStateException("Invalid Status in PulledOffsets. Needs Check PulledOffsets Status");
        }

        return pulledOffsets;
    }

    private boolean validatePulledOffsetStatus(List<PulledOffset> pulledOffsets) {
        return pulledOffsets.stream()
                .anyMatch(a -> a.status().equals(Status.EXIT) || a.status().equals(Status.READY));
    }
}

package org.masil.seoulyeok.pulling.port.outgoing;

import org.masil.seoulyeok.pulling.DetailedPulledOffset;
import org.masil.seoulyeok.pulling.PulledOffset;

public interface UpdatePulledOffsetPort {

    void updatePulledOffsetEntityByCurrentOffset(DetailedPulledOffset detailedPulledOffset);

    void updatePulledOffset(PulledOffset pulledOffset);
}

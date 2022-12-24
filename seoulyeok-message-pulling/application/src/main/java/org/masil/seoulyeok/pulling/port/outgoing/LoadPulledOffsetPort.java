package org.masil.seoulyeok.pulling.port.outgoing;


import org.masil.seoulyeok.pulling.PipelineId;
import org.masil.seoulyeok.pulling.PulledOffset;

import java.util.List;

public interface LoadPulledOffsetPort {

    List<PulledOffset> findAllOnProgressedPulledOffset();

    PulledOffset findByPipelineId(PipelineId pipelineId);
}

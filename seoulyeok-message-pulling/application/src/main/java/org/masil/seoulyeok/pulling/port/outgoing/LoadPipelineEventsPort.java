package org.masil.seoulyeok.pulling.port.outgoing;

import org.masil.seoulyeok.pulling.PipelineId;
import org.masil.seoulyeok.ref.pipeline.PipelineEvent;

public interface LoadPipelineEventsPort {
    PipelineEvent get(PipelineId pipelineId);
}

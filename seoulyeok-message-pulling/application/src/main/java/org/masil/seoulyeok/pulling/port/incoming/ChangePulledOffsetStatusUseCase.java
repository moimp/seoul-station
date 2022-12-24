package org.masil.seoulyeok.pulling.port.incoming;

public interface ChangePulledOffsetStatusUseCase {
    void changeStatus(Long pipelineId, String state);
}

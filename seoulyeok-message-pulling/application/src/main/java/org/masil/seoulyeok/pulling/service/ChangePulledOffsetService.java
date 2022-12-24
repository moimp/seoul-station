package org.masil.seoulyeok.pulling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.pulling.PipelineId;
import org.masil.seoulyeok.pulling.PulledOffset;
import org.masil.seoulyeok.pulling.Status;
import org.masil.seoulyeok.pulling.port.incoming.ChangePulledOffsetStatusUseCase;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPulledOffsetPort;
import org.springframework.stereotype.Service;

import static org.masil.seoulyeok.pulling.Status.EXIT;
import static org.masil.seoulyeok.pulling.Status.ON_PROGRESS;


@Slf4j
@RequiredArgsConstructor
@Service
public class ChangePulledOffsetService implements ChangePulledOffsetStatusUseCase {

    private final PulledOffsetUpdater updater;
    private final LoadPulledOffsetPort loadPulledOffsetPort;

    @Override
    public void changeStatus(Long pipelineId, String statusValue) {
        changeStatus(pipelineId, Status.valueOf(statusValue));
    }

    private void changeStatus(Long pipelineId, Status status) {
        throwWhenNotSupportedType(status);
        if (ON_PROGRESS.equals(status)) {
            start(pipelineId);
            return;
        }

        if (EXIT.equals(status)) {
            stop(pipelineId);
            return;
        }

        throw new IllegalStateException();
    }

    private void start(Long pipelineId) {
        PulledOffset pulledOffset = loadPulledOffsetPort.findByPipelineId(PipelineId.of(pipelineId));
        validateNull(pipelineId, pulledOffset);
        validateStatusIsReady(pipelineId, pulledOffset);

        pulledOffset.start();
        updater.update(pulledOffset);
    }

    private void validateStatusIsReady(Long pipelineId, PulledOffset pulledOffset) {
        if (pulledOffset.isReady() == false) {
            throw new IllegalStateException("이미 시작 또는 종료된 pipeline 입니다. pipelineId: " + pipelineId);
        }
    }

    private void stop(Long pipelineId) {
        PulledOffset pulledOffset = loadPulledOffsetPort.findByPipelineId(PipelineId.of(pipelineId));
        throwWhenIsNull(pipelineId, pulledOffset);
        pulledOffset.stop();
        updater.update(pulledOffset);
    }

    private void validateNull(Long pipelineId, PulledOffset readyOffsets) {
        throwWhenIsNull(pipelineId, readyOffsets);
    }

    private void throwWhenNotSupportedType(Status status) {
        if (!(ON_PROGRESS.equals(status) || EXIT.equals(status))) {
            throw new IllegalStateException("유효하지 않는 OFFSET 상태입니다" + status);
        }
    }

    private void throwWhenIsNull(Long pipelineId, PulledOffset pulledOffset) {
        if (pulledOffset == null) {
            log.error("해당 pipelineId 를 가진 pulled_offset 중에 상태를 변경할 수 있는게 존재하지 않습니다. pipelineId => {}", pipelineId);
            throw new IllegalStateException("해당 pipelineId 를 가진 pulled_offset 중에 상태를 변경할 수 있는게 존재하지 않습니다. pipelineId => " + pipelineId);
        }
    }


}

package org.masil.seoulyeok.pulling.port.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.pulling.*;
import org.masil.seoulyeok.pulling.port.outgoing.LoadPulledOffsetPort;
import org.masil.seoulyeok.pulling.service.PulledOffsetFinder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PulledOffsetFinderTest {

    @InjectMocks
    PulledOffsetFinder sut;

    @Mock
    LoadPulledOffsetPort loadPulledOffsetPort;

    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    private final PulledOffsetId ANY_PULLED_OFFSET_ID = PulledOffsetId.of(1L);
    private final PipelineId ANY_PIPELINE_ID = PipelineId.of(1L);

    private final LocalDateTime ANY_DATE_TIME = LocalDateTime.of(2022, 1, 1, 1, 1, 1);
    private final Position ANY_POSITION = Position.of(ANY_DATE_TIME);
    private final Size ANY_SIZE = Size.of(100);

    @Test
    void 종료된_PULLED_OFFSET_이_반환된_경우_예외가_발생한다() {
        List<PulledOffset> value = new ArrayList<>();
        PulledOffset ANY_PULLED_OFFSET = new PulledOffset(ANY_PULLED_OFFSET_ID, ANY_DESTINATION_ID, ANY_PIPELINE_ID, ANY_POSITION, ANY_DATE_TIME, Status.EXIT);
        value.add(ANY_PULLED_OFFSET);
        given(loadPulledOffsetPort.findAllOnProgressedPulledOffset()).willReturn(value);

        assertThatThrownBy(() -> sut.findAllOnProgressPulledOffset()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void Active_상태의_Offset_을_가져온다() {
        given(loadPulledOffsetPort.findAllOnProgressedPulledOffset()).willReturn(new ArrayList<>());

        List<PulledOffset> allActiveOffsets = sut.findAllOnProgressPulledOffset();

        assertThat(allActiveOffsets).isNotNull();
        verify(loadPulledOffsetPort).findAllOnProgressedPulledOffset();
    }
}

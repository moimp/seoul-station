package kr.co.trevari.eventStore.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.eventstore.DomainEventId;
import org.masil.seoulyeok.eventstore.port.incoming.StoredEventRollbackCommand;
import org.masil.seoulyeok.eventstore.port.outgoing.DeleteEventPort;
import org.masil.seoulyeok.eventstore.port.outgoing.LoadEventPort;
import org.masil.seoulyeok.eventstore.service.StoredEventRollbackService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoredEventRollbackServiceTest {

    public static final DomainEventId EXIST_EVENT_ID = DomainEventId.of(1L);
    public static final DomainEventId NOT_EXIST_EVENT_ID = DomainEventId.of(2L);
    @InjectMocks
    StoredEventRollbackService sut;

    @Mock
    LoadEventPort loadEventPort;
    @Mock
    DeleteEventPort deleteEventPort;

    @Test
    void 롤백시킬_이벤트가_존재하지_않으면_아무일도_일어나지_않는다() {
        StoredEventRollbackCommand command = StoredEventRollbackCommand.of(List.of(NOT_EXIST_EVENT_ID));
        given(loadEventPort.existsEvent(2L)).willReturn(false);

        List<DomainEventId> actual = sut.rollback(command);

        assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    void 롤백시킬_이벤트가_존재하면_삭제한다() {
        StoredEventRollbackCommand command = StoredEventRollbackCommand.of(List.of(EXIST_EVENT_ID));
        given(loadEventPort.existsEvent(1L)).willReturn(true);

        List<DomainEventId> actual = sut.rollback(command);

        assertThat(actual.size()).isEqualTo(0);
        verify(deleteEventPort).delete(EXIST_EVENT_ID);
    }
}

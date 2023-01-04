package org.masil.seoulyeok.events.relay.application.publisher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.publisher.DefaultPublishResult;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.publisher.PublisherContainer;
import org.masil.seoulyeok.events.relaymessage.MessagePayload;
import org.masil.seoulyeok.events.relaymessage.PayloadVersion;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.masil.seoulyeok.events.destination.DestinationType.SIMPLE_QUEUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublisherContainersTest {

    private static final RelayMessageId ANY_RELAY_MESSAGE_ID = RelayMessageId.of(1L);
    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(2L);
    private static final LocalDateTime PUBLISHED_AT = LocalDateTime.now();
    private static final TopRelayMessage ANY_TOP_RELAY_MESSAGE = new TopRelayMessage(RelayMessageId.of(1L),DestinationId.of(1L), MessagePayload.of("xx"), LocalDateTime.now(), null, PayloadVersion.ZERO);

    private static final DefaultPublishResult SUCCESS_PUBLISH_RESULT = DefaultPublishResult.success(
            ANY_RELAY_MESSAGE_ID,
            ANY_DESTINATION_ID,
            PUBLISHED_AT);
    public static final Address ANY_ADDRESS = Address.of("ANY_ADDRESS");
    public static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, ANY_ADDRESS, SIMPLE_QUEUE);

    @InjectMocks
    PublisherContainers sut;

    @Mock
    PublisherContainer container;

    @Test
    void 최초의_컨테이너_크기는_0이다() {
        assertThat(sut.getContainerSize()).isZero();

        sut.add(container);

        assertThat(sut.getContainerSize()).isEqualTo(1);
    }

    @Test
    void SIMPLE_QUEUE_로_publish_하면_호출된다() {
        given(container.isSupportedType(SIMPLE_QUEUE)).willReturn(true);
        given(container.publish(ANY_TOP_RELAY_MESSAGE, ANY_DESTINATION)).willReturn(SUCCESS_PUBLISH_RESULT);
        sut.add(container);
        sut.publish(ANY_TOP_RELAY_MESSAGE, ANY_DESTINATION);

        verify(container).publish(any(TopRelayMessage.class), any(DestinationTarget.class));
    }

    @Test
    void SIMPLE_QUEUE_로_publish_하면_결과를_반환한다() {
        given(container.isSupportedType(SIMPLE_QUEUE)).willReturn(true);
        given(container.publish(ANY_TOP_RELAY_MESSAGE, ANY_DESTINATION)).willReturn(SUCCESS_PUBLISH_RESULT);
        sut.add(container);

        PublishResult actual = sut.publish(ANY_TOP_RELAY_MESSAGE, ANY_DESTINATION);

        assertThat(actual.getRelayMessageId()).isEqualTo(ANY_RELAY_MESSAGE_ID);
        assertThat(actual.isSuccess()).isTrue();
    }

    @Test
    void factory_가_관리하는_container_에_찾고자_하는_containerType_이_없다면_예외를_반환한다() {
        given(container.isSupportedType(SIMPLE_QUEUE)).willReturn(false);
        sut.add(container);

        assertThatThrownBy(() -> sut.publish(ANY_TOP_RELAY_MESSAGE, ANY_DESTINATION)).isInstanceOf(
                NoSuchElementException.class);
    }
}

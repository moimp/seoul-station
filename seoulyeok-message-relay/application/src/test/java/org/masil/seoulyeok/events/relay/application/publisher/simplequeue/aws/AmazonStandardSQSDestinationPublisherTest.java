package org.masil.seoulyeok.events.relay.application.publisher.simplequeue.aws;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessagingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.masil.seoulyeok.events.destination.DestinationType.SIMPLE_QUEUE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AmazonStandardSQSDestinationPublisherTest {

    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(2L);
    private static final Address ANY_ADDRESS = Address.of("ANY_ADDRESS");

    private static final TopRelayMessage TOP_RELAY_MESSAGE = null;
    public static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, ANY_ADDRESS, SIMPLE_QUEUE);

    @InjectMocks
    AmazonStandardSQSDestinationPublisher sut;

    @Mock
    QueueMessagingTemplate queueMessagingTemplate;

    @Test
    @Disabled(value = "sqs 실제 연결하지 않았기 때문에 임시 주석 처리")
    void Destination_으로_메시지_발행에_성공하면_결과는_true() {
        doNothing().when(queueMessagingTemplate)
                .convertAndSend(ANY_DESTINATION.getAddress().getValue(), TOP_RELAY_MESSAGE);

        PublishResult actual = sut.execute(TOP_RELAY_MESSAGE, ANY_DESTINATION);

        assertThat(actual.isSuccess()).isTrue();
    }

    @Test
    @Disabled
    void Destination_으로_메시지_발행에_실패하면_결과는_false() {
        doThrow(new MessagingException("")).when(queueMessagingTemplate)
                .convertAndSend(ANY_DESTINATION.getAddress().getValue(), TOP_RELAY_MESSAGE);

        PublishResult actual = sut.execute(TOP_RELAY_MESSAGE, ANY_DESTINATION);

        assertThat(actual.isSuccess()).isFalse();
    }
}

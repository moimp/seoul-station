package org.masil.seoulyeok.events.relay.application.publisher.simplequeue;

import org.masil.seoulyeok.events.destination.Address;
import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relay.application.publisher.simplequeue.aws.AmazonStandardSQSDestinationPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.masil.seoulyeok.events.destination.DestinationType.SIMPLE_QUEUE;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleQueuePublisherContainerTest {

    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(2L);
    private static final TopRelayMessage TOP_RELAY_MESSAGE = null;
    private static final Address ANY_ADDRESS = Address.of("ANY_ADDRESS");
    private static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, ANY_ADDRESS, SIMPLE_QUEUE);

    SimpleQueuePublisherContainer sut;

    @Mock
    AmazonStandardSQSDestinationPublisher amazonStandardSQSDestinationPublisher;

    @BeforeEach
    void setUp() {
        sut = new SimpleQueuePublisherContainer();
    }

    @Test
    void relay_를_publish_하면_적절한_publisher_가_동작한다() {
        sut.add(amazonStandardSQSDestinationPublisher);

        sut.publish(TOP_RELAY_MESSAGE, ANY_DESTINATION);

        verify(amazonStandardSQSDestinationPublisher).execute(TOP_RELAY_MESSAGE, ANY_DESTINATION);
    }

    @Test
    void publisher_를_추가할_수_있다() {
        assertThat(sut.getContainerSize()).isZero();

        sut.add(amazonStandardSQSDestinationPublisher);

        assertThat(sut.getContainerSize()).isEqualTo(1);
    }
}

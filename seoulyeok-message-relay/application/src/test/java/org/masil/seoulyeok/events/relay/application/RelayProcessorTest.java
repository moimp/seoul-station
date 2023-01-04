package org.masil.seoulyeok.events.relay.application;

import com.likelen.identifier.generator.DummyLongValueGenerator;
import com.likelen.identifier.generator.IdGeneratorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.masil.seoulyeok.events.destination.*;
import org.masil.seoulyeok.events.publisher.DefaultPublishResult;
import org.masil.seoulyeok.events.relay.application.alert.SlackAlertNotifier;
import org.masil.seoulyeok.events.relay.application.config.RelayProcessorConfig;
import org.masil.seoulyeok.events.relay.application.publisher.PublisherContainers;
import org.masil.seoulyeok.events.relaymessage.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RelayProcessorTest {

    public static final LocalDateTime ANY_PUBLISHED_AT = LocalDateTime.of(2022, 1, 1, 1, 1);
    private static final DestinationId ANY_DESTINATION_ID = DestinationId.of(1L);
    public static final Destination ANY_DESTINATION = Destination.of(ANY_DESTINATION_ID, Address.of("any address"),
            DestinationType.SIMPLE_QUEUE, DestinationStatus.ACTIVE);
    public static final MessagePayload ANY_MESSAGE_PAYLOAD = MessagePayload.of("messagePayload1");
    public static final PayloadVersion ANY_PAYLOAD_VERSION = PayloadVersion.ZERO;
    @InjectMocks
    RelayProcessor sut;

    @Mock
    DestinationFetcher fetcher;

    @Mock
    TopMessageFinder topMessageFinder;

    @Mock
    PublisherContainers publisherContainers;

    @Mock
    RelayProcessorConfig relayProcessorConfig;

    @Mock
    RelaidMessageUpdater updater;
    @Mock
    DestinationStatusChanger changer;
    @Mock
    SlackAlertNotifier notifier;


    @BeforeAll
    static void beforeAll() {
        new IdGeneratorFactory(new DummyLongValueGenerator());

    }

    @Test
    void relay_success() {
        RelayMessage message = RelayMessage.create(ANY_MESSAGE_PAYLOAD, ANY_DESTINATION_ID, ANY_PAYLOAD_VERSION);
        TopRelayMessage topRelayMessage = TopRelayMessage.createByMessage(message);
        RelayMessageId id = message.getId();

        DefaultPublishResult success = DefaultPublishResult.success(id, ANY_DESTINATION_ID, ANY_PUBLISHED_AT);

        given(fetcher.findAllActiveDestination()).willReturn(getDestinations());
        given(topMessageFinder.find(ANY_DESTINATION_ID)).willReturn(topRelayMessage);
        given(publisherContainers.publish(topRelayMessage, ANY_DESTINATION)).willReturn(success);
        given(relayProcessorConfig.getBatchSize()).willReturn(1);

        sut.doProcess();

        verify(updater).setMarkRelaidMessage(eq(id), any(LocalDateTime.class));
    }

    @Test
    void relay_fail() {
        RelayMessage message = RelayMessage.create(ANY_MESSAGE_PAYLOAD, ANY_DESTINATION_ID, ANY_PAYLOAD_VERSION);
        TopRelayMessage topRelayMessage = TopRelayMessage.createByMessage(message);
        RelayMessageId id = message.getId();

        DefaultPublishResult fail = DefaultPublishResult.fail(id, ANY_DESTINATION_ID, ANY_PUBLISHED_AT);

        given(fetcher.findAllActiveDestination()).willReturn(getDestinations());
        given(topMessageFinder.find(ANY_DESTINATION_ID)).willReturn(topRelayMessage);
        given(publisherContainers.publish(topRelayMessage, ANY_DESTINATION)).willReturn(fail);
        given(relayProcessorConfig.getBatchSize()).willReturn(1);

        sut.doProcess();

        verifyNoInteractions(updater);
        verify(changer).inactivateBy(ANY_DESTINATION_ID);
        verify(notifier).doNotify(id, ANY_DESTINATION_ID);
    }

    private List<Destination> getDestinations() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(ANY_DESTINATION);
        return destinations;
    }
}

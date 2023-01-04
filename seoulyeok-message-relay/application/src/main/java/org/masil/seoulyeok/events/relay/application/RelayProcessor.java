package org.masil.seoulyeok.events.relay.application;

import org.masil.seoulyeok.events.destination.Destination;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.relay.application.alert.SlackAlertNotifier;
import org.masil.seoulyeok.events.relay.application.config.RelayProcessorConfig;
import org.masil.seoulyeok.events.relay.application.publisher.PublisherContainers;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelayProcessor {

    private final DestinationFetcher fetcher;
    private final TopMessageFinder topMessageFinder;
    private final PublisherContainers publisherContainers;
    private final RelayProcessorConfig relayProcessorConfig;
    private final RelaidMessageUpdater updater;
    private final DestinationStatusChanger changer;
    private final SlackAlertNotifier notifier;

    public void doProcess() {
        List<Destination> activeDestinations = fetcher.findAllActiveDestination();
        int batchSize = relayProcessorConfig.getBatchSize();
        for (Destination activeDestination : activeDestinations) {
            for (int i = 0; i < batchSize; i++) {
                TopRelayMessage topRelayMessage = topMessageFinder.find(activeDestination.getId());
                if (TopRelayMessage.EMPTY.equals(topRelayMessage)) {
                    break;
                }
                PublishResult result = this.publish(topRelayMessage, activeDestination);
                if (!result.isSuccess()) {
                    handleOnFailure(result);
                    break;
                }
                handleOnSuccess(result);
            }
        }
    }

    private void handleOnSuccess(PublishResult result) {
        updater.setMarkRelaidMessage(result.getRelayMessageId(), result.getPublishedAt());
    }

    private void handleOnFailure(PublishResult result) {
        changer.inactivateBy(result.getDestinationId());
        notifier.doNotify(result.getRelayMessageId(), result.getDestinationId());
    }

    private PublishResult publish(TopRelayMessage topRelayMessage, Destination destination) {
        return publisherContainers.publish(topRelayMessage, destination);
    }
}

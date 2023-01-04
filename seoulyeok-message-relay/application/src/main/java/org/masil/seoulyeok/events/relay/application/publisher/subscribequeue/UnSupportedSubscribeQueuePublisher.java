package org.masil.seoulyeok.events.relay.application.publisher.subscribequeue;

import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.publisher.DestinationPublisher;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

@Slf4j
public class UnSupportedSubscribeQueuePublisher implements DestinationPublisher {
    @Override
    public PublishResult execute(TopRelayMessage message, DestinationTarget destination) {
        log.info("unsupported publisher executed, message => {},  destination Target => {}", message, destination);
        return null;
    }
}

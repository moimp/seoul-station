package org.masil.seoulyeok.events.relay.application.publisher.simplequeue.aws;

import com.masil.commons.clocks.Clocks;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.publisher.DefaultPublishResult;
import org.masil.seoulyeok.events.publisher.DestinationPublisher;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.relaymessage.RelayMessageModel;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;
import org.springframework.messaging.MessagingException;

@RequiredArgsConstructor
@Slf4j
public class AmazonStandardSQSDestinationPublisher implements DestinationPublisher {

    private static final String PUBLISH_FAILED_MESSAGE = "Amazon SQS publish failed [message => {} , destination => {}]";
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public PublishResult execute(TopRelayMessage message, DestinationTarget destination) {
        try {
            log.info("assigned message publish => {} !!", message);
            queueMessagingTemplate.convertAndSend(destination.getAddress().getValue(), convert(message));
            return DefaultPublishResult.success(message.getId(), message.getDestinationId(), Clocks.now());
        } catch (MessagingException e) {
            log.info(PUBLISH_FAILED_MESSAGE, message, destination);
            return DefaultPublishResult.fail(message.getId(), message.getDestinationId(), Clocks.now());
        }
    }

    private RelayMessageModel convert(TopRelayMessage message) {
        return RelayMessageModel.of(
                message.getId().get(),
                message.getMessagePayload().getValue(),
                message.getCreateAt().toString(),
                message.getPayloadVersion().getValue());
    }
}

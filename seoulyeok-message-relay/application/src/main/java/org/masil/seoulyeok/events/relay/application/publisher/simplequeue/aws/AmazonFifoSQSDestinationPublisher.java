package org.masil.seoulyeok.events.relay.application.publisher.simplequeue.aws;

import com.masil.commons.clocks.Clocks;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import java.util.HashMap;
import java.util.Map;
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
public class AmazonFifoSQSDestinationPublisher implements DestinationPublisher {

    private static final String PUBLISH_FAILED_MESSAGE = "Amazon Fifo SQS publish failed [message => {} , destination => {}, e => {}]";
    private static final String UNEXPECTED_PUBLISH_FAILED_MESSAGE = "unexpected exception occurred [message => {} , destination => {}, e => {}]";
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public PublishResult execute(TopRelayMessage message, DestinationTarget destination) {
        try {
            log.info("assigned message publish => {} !!", message);

            Map<String, Object> headers = new HashMap<>();
            headers.put("message-group-id", String.valueOf(message.getDestinationId().getValue()));
            headers.put("message-deduplication-id", String.valueOf(message.getId().getValue()));
            queueMessagingTemplate.convertAndSend(destination.getAddress().getValue(), convert(message), headers);
            return DefaultPublishResult.success(message.getId(), message.getDestinationId(), Clocks.now());
        } catch (MessagingException e) {
            log.info(PUBLISH_FAILED_MESSAGE, message, destination, e);
            return DefaultPublishResult.fail(message.getId(), message.getDestinationId(), Clocks.now());
        } catch (Exception e) {
            log.info(UNEXPECTED_PUBLISH_FAILED_MESSAGE, message, destination, e);
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

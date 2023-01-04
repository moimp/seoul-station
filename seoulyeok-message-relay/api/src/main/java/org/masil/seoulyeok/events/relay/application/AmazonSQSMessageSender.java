package org.masil.seoulyeok.events.relay.application;

import com.trevari.domain.core.Serializer;
import org.masil.seoulyeok.events.relay.config.MessageConfig;
import org.masil.seoulyeok.events.relay.models.RelayRequest;
import com.trevari.messages.GeneralMessageEnvelop;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmazonSQSMessageSender implements MessageSender {

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final MessageConfig config;

    @Override
    public void doWork(RelayRequest relayRequest) {
        String messagePayload = relayRequest.getPayload();
        if (!isValid(messagePayload)) {
            throw new IllegalArgumentException("MessagePayload is not Json Type messagePayload: " + messagePayload);
        }
        String queue = config.getFrontControllerQueueName();

        try {
            String serialize = Serializer.getInstance().serialize(relayRequest);

            queueMessagingTemplate.convertAndSend(queue, new GeneralMessageEnvelop<>(serialize));
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private boolean isValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}

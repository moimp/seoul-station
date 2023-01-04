package org.masil.seoulyeok.events.relay.application.alert;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FakeSlackNotifier {

    public static final String RELAY_FAIL_MESSAGE = ":exclamation: Message Relay 에 실패하였습니다.\n ```\nmessageId => [%s], destinationId => [%s]```";

    @Value("${alert.slack.channel}")
    private String ALERT_SLACK_CHANNEL;

    private final RestTemplate rest;

    public String doNotify(RelayMessageId messageId, DestinationId destinationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> body = new HashMap<>();
        body.put("text", getAlertMessage(messageId, destinationId));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        return rest.postForObject(ALERT_SLACK_CHANNEL, request, String.class);
    }

    private String getAlertMessage(RelayMessageId messageId, DestinationId destinationId) {
        return String.format(RELAY_FAIL_MESSAGE, messageId, destinationId);
    }
}

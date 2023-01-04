package org.masil.seoulyeok.events.relay.application.alert;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.masil.seoulyeok.events.destination.DestinationId;
import org.masil.seoulyeok.events.relaymessage.RelayMessageId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackAlertNotifier {

    public static final String RELAY_FAIL_MESSAGE = ":exclamation: Message Relay 에 실패하였습니다.\n ```\nmessageId => [%s], destinationId => [%s]```";
    public static final String FROM = "message-relay";

    @Value("${alert.notifier.url}")
    private String NOTIFIER_URL;

    @Value("${alert.slack.channel}")
    private String ALERT_SLACK_CHANNEL;

    private final RestTemplate rest;

    public void doNotify(RelayMessageId messageId, DestinationId destinationId) {
        SendSlackAlertData request = SendSlackAlertData.of(getAlertMessage(messageId, destinationId),
                List.of(ALERT_SLACK_CHANNEL), FROM);
        String response = rest.postForObject(NOTIFIER_URL + "/slack", request, String.class);
        log.info(response);
    }

    private String getAlertMessage(RelayMessageId messageId, DestinationId destinationId) {
        return String.format(RELAY_FAIL_MESSAGE, messageId, destinationId);
    }
}

package org.masil.seoulyeok.events.relay.application.alert;

import java.util.List;
import lombok.Value;

@Value(staticConstructor = "of")
public class SendSlackAlertData {
    String contents;
    List<String> to;
    String from;
}

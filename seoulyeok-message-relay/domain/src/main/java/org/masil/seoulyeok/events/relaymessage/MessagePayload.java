package org.masil.seoulyeok.events.relaymessage;

import lombok.Value;

@Value(staticConstructor = "of")
public class MessagePayload {
    String value;
}

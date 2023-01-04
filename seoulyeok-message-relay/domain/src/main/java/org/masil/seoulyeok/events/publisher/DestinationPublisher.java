package org.masil.seoulyeok.events.publisher;

import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

public interface DestinationPublisher {
    PublishResult execute(TopRelayMessage message, DestinationTarget destination);
}

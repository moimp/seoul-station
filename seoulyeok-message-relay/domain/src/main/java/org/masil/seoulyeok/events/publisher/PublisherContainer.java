package org.masil.seoulyeok.events.publisher;

import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

public interface PublisherContainer {
    PublishResult publish(TopRelayMessage message, DestinationTarget destination);

    void add(DestinationPublisher publisher);

    boolean isSupportedType(DestinationType type);
}

package org.masil.seoulyeok.events.relay.application.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.publisher.PublisherContainer;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

@RequiredArgsConstructor
public class PublisherContainers {

    private static final String NOT_FOUND_MESSAGE = "not exists Publisher Container type of [%s]";

    private final List<PublisherContainer> containers = new ArrayList<>();

    public PublishResult publish(TopRelayMessage message,
                                 DestinationTarget destination) {
        PublisherContainer container = findByDestinationType(destination.getType());
        return container.publish(message, destination);
    }

    public void add(PublisherContainer container) {
        containers.add(container);
    }

    protected int getContainerSize() {
        return containers.size();
    }

    private PublisherContainer findByDestinationType(DestinationType type) {
        return containers.stream()
                .filter(container -> container.isSupportedType(type))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_MESSAGE, type)));
    }
}

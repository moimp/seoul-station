package org.masil.seoulyeok.events.relay.application.publisher.simplequeue;

import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.publisher.DestinationPublisher;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.publisher.PublisherContainer;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

import java.util.ArrayList;
import java.util.List;

public class SimpleQueuePublisherContainer implements PublisherContainer {

    private final List<DestinationPublisher> publishers = new ArrayList<>();

    @Override
    public PublishResult publish(TopRelayMessage message, DestinationTarget destination) {
        DestinationPublisher publisher = find(destination);
        //TODO Spec & Condition
        return publisher.execute(message, destination);
    }

    private DestinationPublisher find(DestinationTarget message) {
        return publishers.get(0);
    }

    @Override
    public void add(DestinationPublisher publisher) {
        publishers.add(publisher);
    }

    @Override
    public boolean isSupportedType(DestinationType type) {
        return DestinationType.SIMPLE_QUEUE.equals(type);
    }

    protected int getContainerSize() {
        return publishers.size();
    }
}

package org.masil.seoulyeok.events.relay.application.publisher.subscribequeue;


import org.masil.seoulyeok.events.destination.DestinationTarget;
import org.masil.seoulyeok.events.destination.DestinationType;
import org.masil.seoulyeok.events.publisher.DestinationPublisher;
import org.masil.seoulyeok.events.publisher.PublishResult;
import org.masil.seoulyeok.events.publisher.PublisherContainer;
import org.masil.seoulyeok.events.relaymessage.TopRelayMessage;

import java.util.ArrayList;
import java.util.List;

public class SubscribeQueuePublisherContainer implements PublisherContainer {

    private final List<DestinationPublisher> publishers = new ArrayList<>();

    @Override
    public PublishResult publish(TopRelayMessage message, DestinationTarget destination) {
        DestinationPublisher publisher = find();
        //TODO Spec & Condition
        return publisher.execute(message, destination);
    }

    @Override
    public void add(DestinationPublisher publisher) {
        publishers.add(publisher);
    }

    @Override
    public boolean isSupportedType(DestinationType type) {
        return DestinationType.SUBSCRIBER_QUEUE.equals(type);
    }

    protected int getContainerSize() {
        return publishers.size();
    }

    private DestinationPublisher find() {
        // TODO change, 현재는 한가지 type (sqs) 만 존재하므로 하드코딩됨
        return publishers.get(0);
    }
}

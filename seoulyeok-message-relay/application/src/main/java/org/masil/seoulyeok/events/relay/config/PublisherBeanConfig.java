package org.masil.seoulyeok.events.relay.config;

import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.masil.seoulyeok.events.relay.application.publisher.PublisherContainers;
import org.masil.seoulyeok.events.relay.application.publisher.simplequeue.SimpleQueuePublisherContainer;
import org.masil.seoulyeok.events.relay.application.publisher.simplequeue.aws.AmazonFifoSQSDestinationPublisher;
import org.masil.seoulyeok.events.relay.application.publisher.subscribequeue.SubscribeQueuePublisherContainer;
import org.masil.seoulyeok.events.relay.application.publisher.subscribequeue.UnSupportedSubscribeQueuePublisher;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherBeanConfig {

    @Bean
    public QueueMessagingTemplate messagingTemplate() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new QueueMessagingTemplate(AmazonSQSAsyncClientBuilder.defaultClient(), null, objectMapper);
    }


    @Bean
    public PublisherContainers publisherContainers(QueueMessagingTemplate queueMessagingTemplate) {
        PublisherContainers containers = new PublisherContainers();

        containers.add(getSimpleQueuePublisherContainer(queueMessagingTemplate));
        containers.add(getSubscribeQueuePublisherContainer());

        return containers;
    }

    private SimpleQueuePublisherContainer getSimpleQueuePublisherContainer(
            QueueMessagingTemplate queueMessagingTemplate) {

        SimpleQueuePublisherContainer container = new SimpleQueuePublisherContainer();
        container.add(new AmazonFifoSQSDestinationPublisher(queueMessagingTemplate));

        return container;
    }

    private SubscribeQueuePublisherContainer getSubscribeQueuePublisherContainer() {
        SubscribeQueuePublisherContainer container = new SubscribeQueuePublisherContainer();
        // TODO add SNS or Kafka ...
        container.add(new UnSupportedSubscribeQueuePublisher());
        return container;
    }
}

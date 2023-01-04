package org.masil.seoulyeok.events.relay.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSQSConfig implements MessageConfig {

    @Value("${aws.sqs.pulled-events-received}")
    private String frontControllerQueueName;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Override
    public String getFrontControllerQueueName() {
        return frontControllerQueueName;
    }
}

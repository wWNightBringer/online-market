package com.app.common.config.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
            .name("order_topic")
            .partitions(3)
            .replicas(1)
            .build();
    }
}

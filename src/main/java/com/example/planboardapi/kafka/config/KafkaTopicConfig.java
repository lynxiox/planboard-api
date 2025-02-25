package com.example.planboardapi.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String emailSendingTask = "email-sending-task";

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder
                .name(emailSendingTask)
                .build();
    }
}

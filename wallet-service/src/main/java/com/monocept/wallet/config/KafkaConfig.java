package com.monocept.wallet.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic kafkaTopic() {
        return TopicBuilder.name("paymentcompletedevent").build();

    }

    @Bean
    public NewTopic kafkaTopic1() {
        return TopicBuilder.name("paymentfailedevent").build();

    }
}

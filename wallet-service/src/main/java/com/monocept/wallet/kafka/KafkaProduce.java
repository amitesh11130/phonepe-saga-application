package com.monocept.wallet.kafka;

import com.monocept.common.dtos.PaymentCompletedEvent;
import com.monocept.common.dtos.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProduce {

    private final KafkaTemplate<String, PaymentFailedEvent> kafkaTemplate;
    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate1;


    public void sendFailedMessage(String topic, PaymentFailedEvent messageContent) {
        log.info("Sending message to Kafka topic '{}'. Message content: {}", topic, messageContent);

        kafkaTemplate.send(topic, messageContent);

        log.info("Message successfully sent to Kafka topic '{}'", topic);
    }

    public void sendSuccessMessage(String topic, PaymentCompletedEvent messageContent) {
        log.info("Sending message to Kafka topic '{}'. Message content: {}", topic, messageContent);

        kafkaTemplate1.send(topic, messageContent);

        log.info("Message successfully sent to Kafka topic '{}'", topic);
    }
}

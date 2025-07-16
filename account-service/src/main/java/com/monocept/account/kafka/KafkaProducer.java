package com.monocept.account.kafka;

import com.monocept.common.dtos.PaymentInitiatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate;

    public void sendMessage(PaymentInitiatedEvent messageContent) {
        log.info("Sending message to Kafka topic 'paymentinitiatedevent'. Message content: {}", messageContent);

        kafkaTemplate.send("paymentinitiatedevent", messageContent);

        log.info("Message successfully sent to Kafka topic 'paymentinitiatedevent'");
    }


}

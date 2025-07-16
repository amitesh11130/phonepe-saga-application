package com.monocept.account.kafka;

import com.monocept.account.entity.PaymentTransaction;
import com.monocept.account.service.PaymentTransactionService;
import com.monocept.common.dtos.PaymentCompletedEvent;
import com.monocept.common.dtos.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final PaymentTransactionService transactionService;

    @KafkaListener(topics = "paymentfailedevent", groupId = "payment-service-group")
    public void consumeMessageForFailed(PaymentFailedEvent response) {
        log.info("Received message from Kafka topic 'paymentfailedevent' in consumer group" +
                " 'payment_failed_event'. Message content: {}", response);
        PaymentTransaction paymentTransaction = transactionService.updatePaymentFailedTransaction(response);
        log.info("Payment transaction updated successful : {}", paymentTransaction);
    }

    @KafkaListener(topics = "paymentcompletedevent", groupId = "payment-service-group")
    public void consumeMessageForComplete(PaymentCompletedEvent response) {
        log.info("Received message from Kafka topic 'paymentcompletedevent' in consumer group" +
                " 'payment_completed_event'. Message content: {}", response);
        PaymentTransaction paymentTransaction = transactionService.updatePaymentSuccessTransaction(response);
        log.info("Payment transaction updated successful : {}", paymentTransaction);
    }

}

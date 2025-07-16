package com.monocept.history.kafka;

import com.monocept.common.dtos.PaymentCompletedEvent;
import com.monocept.common.dtos.PaymentFailedEvent;
import com.monocept.history.entity.TransactionRecord;
import com.monocept.history.repository.TransactionRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionRecordConsumer {

    private final TransactionRecordRepository recordRepository;

    @KafkaListener(topics = "paymentcompletedevent", groupId = "payment_completed_event")
    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Recording successful transaction: {} ", event.getTransactionId());
        TransactionRecord record = TransactionRecord.builder()
                .transactionId(event.getTransactionId())
                .senderAccountNumber(event.getSenderAccountNumber())
                .receiverAccountNumber(event.getReceiverAccountNumber())
                .amount(event.getAmount())
                .status(TransactionRecord.TransactionStatus.COMPLETED)
                .timestamp(event.getCompletedAt())
                .notes("Payment successful")
                .build();
        recordRepository.save(record);
    }

    @KafkaListener(topics = "paymentfailedevent", groupId = "payment_failed_event")
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.warn("Recording failed transaction: {}", event.getTransactionId());
        TransactionRecord record = TransactionRecord.builder()
                .transactionId(event.getTransactionId())
                .senderAccountNumber(event.getSenderAccountNumber())
                .status(TransactionRecord.TransactionStatus.FAILED)
                .timestamp(event.getFailedAt())
                .notes("Failure: " + event.getFailureReason())
                .build();
        recordRepository.save(record);
    }
}


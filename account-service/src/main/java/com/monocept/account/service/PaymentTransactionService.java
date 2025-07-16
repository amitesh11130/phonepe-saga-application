package com.monocept.account.service;

import com.monocept.account.entity.PaymentTransaction;
import com.monocept.account.kafka.KafkaProducer;
import com.monocept.account.repository.PaymentTransactionRepository;
import com.monocept.account.request.PaymentRequest;
import com.monocept.common.dtos.PaymentCompletedEvent;
import com.monocept.common.dtos.PaymentFailedEvent;
import com.monocept.common.dtos.PaymentInitiatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository transactionRepository;
    private final KafkaProducer kafkaProducer;


    @Transactional
    public String initiatePayment(PaymentRequest paymentRequest) {

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setSenderAccountNumber(paymentRequest.getSenderAccountNumber());
        transaction.setReceiverAccountNumber(paymentRequest.getReceiverAccountNumber());
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setStatus(PaymentTransaction.PaymentStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        PaymentTransaction save = transactionRepository.save(transaction);

        PaymentInitiatedEvent event = PaymentInitiatedEvent.builder()
                .transactionId(save.getPaymentId())
                .senderAccountNumber(paymentRequest.getSenderAccountNumber())
                .receiverAccountNumber(paymentRequest.getReceiverAccountNumber())
                .amount(paymentRequest.getAmount())
                .build();
        kafkaProducer.sendMessage(event);
        return save.getPaymentId().toString();
    }

    public PaymentTransaction updatePaymentFailedTransaction(PaymentFailedEvent failedEvent) {
        Optional<PaymentTransaction> byId = transactionRepository.findById(failedEvent.getTransactionId());

        if (byId.isPresent()) {
            PaymentTransaction paymentTransaction = byId.get();
            paymentTransaction.setStatus(PaymentTransaction.PaymentStatus.FAILED);
            paymentTransaction.setUpdatedAt(failedEvent.getFailedAt());
            paymentTransaction.setFailureReason(failedEvent.getFailureReason());
            return transactionRepository.save(paymentTransaction);
        }
        return null;
    }

    public PaymentTransaction updatePaymentSuccessTransaction(PaymentCompletedEvent completedEvent) {
        Optional<PaymentTransaction> byId = transactionRepository.findById(completedEvent.getTransactionId());

        if (byId.isPresent()) {
            PaymentTransaction paymentTransaction = byId.get();
            paymentTransaction.setStatus(PaymentTransaction.PaymentStatus.SUCCESS);
            paymentTransaction.setUpdatedAt(completedEvent.getCompletedAt());
            return transactionRepository.save(paymentTransaction);
        }
        return null;
    }

    public List<PaymentTransaction> fetchAllRecord() {
        return transactionRepository.findAll();
    }
}

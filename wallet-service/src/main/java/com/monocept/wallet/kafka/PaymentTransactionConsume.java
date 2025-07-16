package com.monocept.wallet.kafka;

import com.monocept.common.dtos.PaymentCompletedEvent;
import com.monocept.common.dtos.PaymentFailedEvent;
import com.monocept.common.dtos.PaymentInitiatedEvent;
import com.monocept.wallet.entity.UserWallet;
import com.monocept.wallet.repository.UserWalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionConsume {

    private final UserWalletRepository walletRepository;
    private final KafkaProduce kafkaProduce;

    @KafkaListener(topics = "paymentinitiatedevent", groupId = "payment_initiated_event")
    @Transactional
    public void handlePaymentInitiated(PaymentInitiatedEvent event) {
        log.info("Handling payment initiated event : {} ", event.getTransactionId());

        try {
            UserWallet senderWallet = walletRepository.findByAccountNumber(event.getSenderAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Sender wallet not found."));

            UserWallet receiverWallet = walletRepository.findByAccountNumber(event.getReceiverAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Receiver wallet not found."));

            if (senderWallet.getBalance().compareTo(event.getAmount()) < 0) {
                throw new RuntimeException("Insufficient funds.");
            }
            senderWallet.setBalance(senderWallet.getBalance() - event.getAmount());
            receiverWallet.setBalance(receiverWallet.getBalance() + event.getAmount());

            walletRepository.save(senderWallet);
            walletRepository.save(receiverWallet);

            PaymentCompletedEvent completedEvent = PaymentCompletedEvent.builder()
                    .transactionId(event.getTransactionId())
                    .senderAccountNumber(event.getSenderAccountNumber())
                    .receiverAccountNumber(event.getReceiverAccountNumber())
                    .amount(event.getAmount())
                    .completedAt(LocalDateTime.now())
                    .build();
            kafkaProduce.sendSuccessMessage("paymentcompletedevent", completedEvent);

            log.info("Published payment completed event for : {}", event.getTransactionId());

        } catch (Exception e) {

            PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
                    .transactionId(event.getTransactionId())
                    .senderAccountNumber(event.getSenderAccountNumber())
                    .failedAt(LocalDateTime.now())
                    .failureReason(e.getMessage())
                    .build();

            kafkaProduce.sendFailedMessage("paymentfailedevent", failedEvent);

            log.error("Published payment failed event for: {}  Reason : {}", event.getTransactionId(), e.getMessage());
        }
    }
}

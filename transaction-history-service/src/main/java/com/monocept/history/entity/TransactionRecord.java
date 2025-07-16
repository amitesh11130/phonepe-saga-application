package com.monocept.history.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRecord {
    @Id
    private Integer transactionId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String notes;

    public enum TransactionStatus {COMPLETED, FAILED}
}

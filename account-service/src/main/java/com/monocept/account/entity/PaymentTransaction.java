package com.monocept.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String failureReason;

    public enum PaymentStatus { PENDING, SUCCESS, FAILED }
}


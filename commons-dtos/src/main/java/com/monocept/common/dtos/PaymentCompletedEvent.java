package com.monocept.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedEvent {
    private Integer transactionId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    private LocalDateTime completedAt;
}

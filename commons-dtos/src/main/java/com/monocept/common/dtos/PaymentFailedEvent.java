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
public class PaymentFailedEvent {
    private Integer transactionId;
    private String senderAccountNumber;
    private String failureReason;
    private LocalDateTime failedAt;
}

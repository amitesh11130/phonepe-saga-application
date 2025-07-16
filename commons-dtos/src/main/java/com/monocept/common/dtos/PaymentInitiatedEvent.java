package com.monocept.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInitiatedEvent {

    private Integer transactionId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
}
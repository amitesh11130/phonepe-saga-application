package com.monocept.account.request;

import lombok.Data;

@Data
public class PaymentRequest {

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;

}

package com.monocept.account.controller;

import com.monocept.account.entity.PaymentTransaction;
import com.monocept.account.request.PaymentRequest;
import com.monocept.account.response.ApiResponse;
import com.monocept.account.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService transactionService;

    @PostMapping("/transfer")
    public ApiResponse initiatePayment(@RequestBody PaymentRequest request) {
        try {
            String transactionId = transactionService.initiatePayment(request);
            ApiResponse.Meta meta = ApiResponse.Meta.builder().code(HttpStatus.ACCEPTED.value()).status(true).description("Payment initiated successfully. Awaiting confirmation.").build();
            return ApiResponse.builder().meta(meta).data("Payment initiated with transaction ID: " + transactionId).build();
        } catch (Exception e) {
            ApiResponse.Meta meta = ApiResponse.Meta.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).status(false).description("Something went wrong! please try again.").build();
            return ApiResponse.builder().meta(meta).error("Failed to initiate payment: " + e.getMessage()).build();
        }
    }

    @GetMapping("/getAll")
    public ApiResponse fetchAllRecord() {
        List<PaymentTransaction> transactionList = transactionService.fetchAllRecord();
        ApiResponse.Meta meta = ApiResponse.Meta.builder().code(HttpStatus.ACCEPTED.value()).status(true).description("Fetch all record").build();
        return ApiResponse.builder().meta(meta).data(transactionList).build();

    }

}

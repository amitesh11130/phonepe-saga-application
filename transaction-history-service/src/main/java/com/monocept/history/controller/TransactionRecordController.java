package com.monocept.history.controller;

import com.monocept.history.entity.TransactionRecord;
import com.monocept.history.service.TransactionRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class TransactionRecordController {

    private final TransactionRecordService recordService;


    @GetMapping("/getByAccNo")
    public List<TransactionRecord> getHistory(@RequestParam String senderAccountNumber,
                                              @RequestParam String receiverAccountNumber) {
        return recordService.getHistory(senderAccountNumber, receiverAccountNumber);
    }

}

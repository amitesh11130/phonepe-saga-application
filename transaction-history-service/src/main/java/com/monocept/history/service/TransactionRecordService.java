package com.monocept.history.service;

import com.monocept.history.entity.TransactionRecord;
import com.monocept.history.repository.TransactionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionRecordService {

    private final TransactionRecordRepository recordRepository;

    public List<TransactionRecord> getHistory(String senderAccountNumber, String receiverAccountNumber) {
        return recordRepository.findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc(senderAccountNumber, receiverAccountNumber);
    }
}

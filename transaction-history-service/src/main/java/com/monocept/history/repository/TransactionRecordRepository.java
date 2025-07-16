package com.monocept.history.repository;

import com.monocept.history.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Integer> {
    List<TransactionRecord> findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc(String senderAccountNumber, String receiverAccountNumber);
}

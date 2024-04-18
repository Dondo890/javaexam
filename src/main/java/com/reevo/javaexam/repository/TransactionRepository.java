package com.reevo.javaexam.repository;

import com.reevo.javaexam.model.TransactionData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface TransactionRepository {
    Optional<TransactionData> findTransactionDataByTransactionId(int transactionId);
    void save(TransactionData data);
}

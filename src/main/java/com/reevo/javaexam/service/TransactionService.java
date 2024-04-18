package com.reevo.javaexam.service;

import com.reevo.javaexam.exception.TransactionException;
import com.reevo.javaexam.model.TransactionData;
import com.reevo.javaexam.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public int startTransaction(int transactionId){
        TransactionData data = getTransactionData(transactionId);
        if(data.isProcessing()){
            throw new TransactionException(TransactionException.TRANSACTION_STILL_PROCESSING);
        }
        data.setProcessing(true);
        repository.save(data);
        return data.getTransactionId();
    }

    public int commitTransaction(int transactionId) throws TransactionException{
        TransactionData data = getTransactionData(transactionId);
        if(data.isCommitted()){
            throw new TransactionException(TransactionException.TRANSACTION_COMMITTED);
        }
        data.setCommitted(true);
        data.setProcessing(false);
        repository.save(data);
        return data.getTransactionId();
    }

    public int rollbackTransaction(int transactionId) throws TransactionException{
        TransactionData data = getTransactionData(transactionId);
        if(!data.isCommitted()){
            throw new TransactionException(TransactionException.TRANSACTION_NOT_COMMITTED);
        }
        data.setCommitted(false);
        data.setProcessing(false);
        repository.save(data);
        return data.getTransactionId();
    }

    private TransactionData getTransactionData(int transactionId) throws TransactionException{
        Optional<TransactionData>transactionDataOptional =
                repository.findTransactionDataByTransactionId(transactionId);
        if(transactionDataOptional.isEmpty()){
            throw new TransactionException(TransactionException.INVALID_TRANSACTION_ID);
        }
        return transactionDataOptional.get();
    }

}

package com.reevo.javaexam.controller;

import com.reevo.javaexam.exception.TransactionException;
import com.reevo.javaexam.response.TransactionResponse;
import com.reevo.javaexam.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/start-transaction/{transactionId}")
    public ResponseEntity<TransactionResponse>startTransaction(@PathVariable("transactionId")int transactionId){
        TransactionResponse response = new TransactionResponse();
        try{
            int id = service.startTransaction(transactionId);
            response.setMessage(String.format("Transaction ID: %d started.", id));
            response.setStatus(200);
        }catch (TransactionException ex){
            switch (ex.getMessage()){
                case TransactionException.INVALID_TRANSACTION_ID:
                    response.setStatus(404);
                    response.setMessage("Transaction ID not Found");
                    return ResponseEntity.status(404).body(response);
                case TransactionException.TRANSACTION_STILL_PROCESSING:
                    response.setStatus(409);
                    response.setMessage("Transaction ID is Processed before");
                    return ResponseEntity.status(409).body(response);
            }
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/commit-transaction/{transactionId}")
    public ResponseEntity<TransactionResponse> commitTransaction(
            @PathVariable("transactionId") int transactionId) {
        TransactionResponse response = new TransactionResponse();
        try{
            int id = service.startTransaction(transactionId);
            response.setMessage(String.format("Transaction ID: %d Committed.", id));
            response.setStatus(200);
        }catch (TransactionException ex){
            switch (ex.getMessage()){
                case TransactionException.INVALID_TRANSACTION_ID:
                    response.setStatus(404);
                    response.setMessage("Transaction ID not Found");
                    return ResponseEntity.status(404).body(response);
                case TransactionException.TRANSACTION_COMMITTED:
                    response.setStatus(409);
                    response.setMessage("Transaction ID already committed");
                    return ResponseEntity.status(409).body(response);
            }
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/rollback-transaction/{transactionId}")
    public ResponseEntity<TransactionResponse> rollbackTransaction(
            @PathVariable("transactionId") int transactionId) {
        TransactionResponse response = new TransactionResponse();
        try{
            int id = service.startTransaction(transactionId);
            response.setMessage(String.format("Transaction ID: %d Committed.", id));
            response.setStatus(200);
        }catch (TransactionException ex){
            switch (ex.getMessage()){
                case TransactionException.INVALID_TRANSACTION_ID:
                    response.setStatus(404);
                    response.setMessage("Transaction ID not Found");
                    return ResponseEntity.status(404).body(response);
                case TransactionException.TRANSACTION_NOT_COMMITTED:
                    response.setStatus(409);
                    response.setMessage("Transaction ID not committed");
                    return ResponseEntity.status(409).body(response);
            }
        }
        return ResponseEntity.status(200).body(response);
    }
}

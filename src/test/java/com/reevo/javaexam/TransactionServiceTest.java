package com.reevo.javaexam;

import com.reevo.javaexam.exception.TransactionException;
import com.reevo.javaexam.model.TransactionData;
import com.reevo.javaexam.repository.TransactionRepository;
import com.reevo.javaexam.service.TransactionService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private TransactionService service;
    @MockBean
    @Autowired
    private TransactionRepository repository;

    private List<TransactionData> mockDataList = new ArrayList<>();

    @BeforeEach
    public void populateDataList(){
        MockitoAnnotations.initMocks(this);
        TransactionData successTransactionData = new TransactionData();
        successTransactionData.setTransactionId(1);
        successTransactionData.setName("Ron");
        successTransactionData.setAge(25);
        mockDataList.add(successTransactionData);
        TransactionData errorStartTransactionData = new TransactionData();
        errorStartTransactionData.setTransactionId(2);
        errorStartTransactionData.setProcessing(true);
        errorStartTransactionData.setName("Ron");
        errorStartTransactionData.setAge(25);
        mockDataList.add(errorStartTransactionData);
        TransactionData errorCommitTransactionData = new TransactionData();
        errorCommitTransactionData.setTransactionId(3);
        errorCommitTransactionData.setProcessing(true);
        errorCommitTransactionData.setName("Ron");
        errorCommitTransactionData.setAge(25);
        mockDataList.add(errorCommitTransactionData);
        TransactionData errorRollbackTransactionData = new TransactionData();
        errorRollbackTransactionData.setTransactionId(3);
        errorRollbackTransactionData.setProcessing(true);
        successTransactionData.setName("Ron");
        successTransactionData.setAge(25);
        mockDataList.add(errorRollbackTransactionData);
    }

    @Test
    public void startTransaction_Success(){
        int transactionId = 1;
        when(service.startTransaction(transactionId)).
                thenReturn(mockDataList.stream()
                        .filter(data -> data.getTransactionId() == transactionId).findFirst().get().getTransactionId());
        int idReturned = service.startTransaction(transactionId);
        assertEquals(idReturned, transactionId);
    }

    @Test
    public void startTransaction_InvalidTransactionId(){
        int invalidTransactionId = 6;
        when(repository.findTransactionDataByTransactionId(invalidTransactionId))
                .thenReturn(mockDataList.stream()
                        .filter(data -> data.getTransactionId() == invalidTransactionId).findFirst());

        TransactionException exception = assertThrows(TransactionException.class, ()->{
            service.startTransaction(invalidTransactionId);
        });
        assertEquals(TransactionException.INVALID_TRANSACTION_ID, exception.getMessage());

    }

}

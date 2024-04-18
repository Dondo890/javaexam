package com.reevo.javaexam.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionData {
    private int transactionId;
    private String name;
    private int age;
    private boolean isProcessing = false;
    private boolean isCommitted = false;
}

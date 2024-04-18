package com.reevo.javaexam.exception;

public class TransactionException extends RuntimeException{

    public static final String INVALID_TRANSACTION_ID = "Invalid Transaction ID";
    public static final String TRANSACTION_STILL_PROCESSING = "Transaction ID is Processing";
    public static final String TRANSACTION_COMMITTED = "Transaction ID already committed";
    public static final String TRANSACTION_NOT_COMMITTED = "Transaction ID is not yet committed";

    public TransactionException(){
        super();
    }

    public TransactionException(String message){
        super(message);
    }

}

package com.boardcamp.api.exceptions.customer_errors;

public class CustomerCpfConflictException extends RuntimeException{
    
    public CustomerCpfConflictException(String message){
        super(message);
    }
}

package com.boardcamp.api.exceptions;

public class CustomerCpfConflictException extends RuntimeException{
    
    public CustomerCpfConflictException(String message){
        super(message);
    }
}

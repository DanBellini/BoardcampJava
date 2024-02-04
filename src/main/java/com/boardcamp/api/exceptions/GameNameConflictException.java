package com.boardcamp.api.exceptions;

public class GameNameConflictException extends RuntimeException {
    
    public GameNameConflictException(String message){
        super(message);
    }
}

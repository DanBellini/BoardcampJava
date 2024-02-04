package com.boardcamp.api.exceptions.game_errors;

public class GameNameConflictException extends RuntimeException {
    
    public GameNameConflictException(String message){
        super(message);
    }
}

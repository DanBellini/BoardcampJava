package com.boardcamp.api.exceptions.game_errors;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException (String message){
        super(message);
    }
}

package com.boardcamp.api.exceptions;

public class RentalNotAvailableStockException extends RuntimeException {
    public RentalNotAvailableStockException (String message){
        super(message);
    }
}

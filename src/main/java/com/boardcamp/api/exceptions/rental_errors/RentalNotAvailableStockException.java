package com.boardcamp.api.exceptions.rental_errors;

public class RentalNotAvailableStockException extends RuntimeException {
    public RentalNotAvailableStockException (String message){
        super(message);
    }
}

package com.boardcamp.api.exceptions.rental_errors;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(String message){
        super(message);
    }
}

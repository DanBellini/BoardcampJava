package com.boardcamp.api.exceptions.rental_errors;

public class RentalAlreadyReturnedException extends RuntimeException{
    public RentalAlreadyReturnedException (String message){
        super(message);
    }
}

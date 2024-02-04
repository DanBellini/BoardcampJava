package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boardcamp.api.exceptions.customer_errors.CustomerCpfConflictException;
import com.boardcamp.api.exceptions.customer_errors.CustomerNotFoundException;
import com.boardcamp.api.exceptions.game_errors.GameNameConflictException;
import com.boardcamp.api.exceptions.game_errors.GameNotFoundException;
import com.boardcamp.api.exceptions.rental_errors.RentalAlreadyReturnedException;
import com.boardcamp.api.exceptions.rental_errors.RentalNotAvailableStockException;
import com.boardcamp.api.exceptions.rental_errors.RentalNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({GameNameConflictException.class})
    public ResponseEntity<String> handlerGameNameConflict(GameNameConflictException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<String> handlerCustomerNotFound(CustomerNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({CustomerCpfConflictException.class})
    public ResponseEntity<String> handlerCustomerCpfConflict(CustomerCpfConflictException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({GameNotFoundException.class})
    public ResponseEntity<String> handlerGameNotFound(GameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({RentalNotAvailableStockException.class})
    public ResponseEntity<String> handlerRentalNotPossible(RentalNotAvailableStockException exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({RentalNotFoundException.class})
    public ResponseEntity<String> handlerRentalNotFound(RentalNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({RentalAlreadyReturnedException.class})
    public ResponseEntity<String> handlerRentalAlreadyReturned(RentalAlreadyReturnedException exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
}

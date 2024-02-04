package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomerDto;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.services.CustomerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
public class CustomerController {
    
    final CustomerService customersService;

    public CustomerController(CustomerService customersService){
        this.customersService = customersService;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerModel> createNewCustomers(@RequestBody @Valid CustomerDto body) {
        CustomerModel customer = customersService.save(body);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
}

package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDto;
import com.boardcamp.api.exceptions.customer_errors.CustomerCpfConflictException;
import com.boardcamp.api.exceptions.customer_errors.CustomerNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @SuppressWarnings("null")
    public CustomerModel findById(Long id){
        return customerRepository.findById(id).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found by this id!"));
    }

    public CustomerModel save (CustomerDto dto){
        if(customerRepository.existsByCpf(dto.getCpf())){
            throw new CustomerCpfConflictException("This CPF already exist!");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }

}

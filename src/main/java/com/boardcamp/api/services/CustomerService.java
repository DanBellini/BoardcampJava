package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDto;
import com.boardcamp.api.exceptions.CustomerCpfConflictException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerModel save (CustomerDto dto){
        if(customerRepository.existsByCpf(dto.getCpf())){
            throw new CustomerCpfConflictException("This CPF already exist!");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }

}

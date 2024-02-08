package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.CustomerDto;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomersIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void cleanUpDatabase(){
        customerRepository.deleteAll();
    }

    @Test
    void givenValidCustomerId_whenFindCustomer_thenReturnCustomer(){
        //given
        CustomerModel customer = createAndSaveCustomer("Test", "01234567890");

        //when
        ResponseEntity<CustomerModel> response = restTemplate.exchange(
            "/customers/{id}",
            HttpMethod.GET,
            null,
            CustomerModel.class,
            customer.getId()
            );
        
        //then
        assertNotNull(response.getBody());
        assertEquals(customer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }

    @Test
    void givenInvalidCustomerId_whenFindCustomer_thenThrowsErrorNotFound(){
        //given
        Random random = new Random();
        Long randomNumber = random.nextLong();

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            "/customers/{id}",
            HttpMethod.GET,
            null,
            String.class,
            randomNumber
            );

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, customerRepository.count());
    }

    @Test
    void givenValidCustomer_whenCreatingCustomer_thenCreateCustomer(){
        //given
        CustomerDto customer = new CustomerDto("Test", "01234567890");
        HttpEntity<CustomerDto> body = new HttpEntity<>(customer);

        //when
        ResponseEntity<CustomerModel> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            CustomerModel.class
            );

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }

    @Test
    void givenRepeatedCustomerCPF_whenCreatingCustomer_thenThrowsErrorConflict(){
        //given
        createAndSaveCustomer("TestA", "01234567890");
        
        CustomerDto customer = new CustomerDto("TestB", "01234567890");
        HttpEntity<CustomerDto> body = new HttpEntity<>(customer);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            String.class
            );

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }

    @Test
    void givenInvalidCustomerDTO_whenCreatingCustomer_thenThrowErrorBadRequest(){
        //given
        CustomerDto customer = new CustomerDto("", "");
        HttpEntity<CustomerDto> body = new HttpEntity<>(customer);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            String.class
            );

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, customerRepository.count());
    }

    //helper methods
    private CustomerModel createAndSaveCustomer (String name, String cpf){
        CustomerModel customer = new CustomerModel(null, name, cpf);
        return customerRepository.save(customer);
    }
}

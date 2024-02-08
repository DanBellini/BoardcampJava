package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.RentalDto;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentalIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @AfterEach
    void cleanUpDatabase(){
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    private final String RENTAL_ENDPOINT = "/rentals";

    @SuppressWarnings("null")
    @Test
	void givenRepositoryIsEmpty_whenGetRequestFindAllRentals_thenReturnArrayEmpty() {
		//given

		//when
		ResponseEntity<List<RentalModel>> response = restTemplate.exchange(
			RENTAL_ENDPOINT,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<RentalModel>>() {}
			);

		//then
		assertTrue(response.getBody().isEmpty());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, rentalRepository.count());
	}

    @SuppressWarnings("null")
    @Test
    void givenRepositoryIsNotEmpty_whenGetRequestFindAllRentals_thenReturnArrayRentalModel(){
        //given
        GameModel game1 = createAndSaveGame("Xadrez", "imageTest.com", 3, 1000);
        GameModel game2 = createAndSaveGame("Monopoly", "imageTest.com", 2, 500);
        
        CustomerModel customer1 = createAndSaveCustomer("TestA","01234567890");
        CustomerModel customer2 = createAndSaveCustomer("TestB", "09876543210");
        
        RentalModel rental1 = createAndSaveRental(3, customer1, game1);
        RentalModel rental2 = createAndSaveRental(2, customer1, game2);
        RentalModel rental3 = createAndSaveRental(5, customer2, game1);

        //when
        ResponseEntity<List<RentalModel>> response = restTemplate.exchange(
            RENTAL_ENDPOINT,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<RentalModel>>() {}
            );

        //then
        List<RentalModel> rentals = response.getBody();

        assertEquals(3, rentals.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, rentalRepository.count());

        assertTrue(rentals.contains(rental1));
        assertTrue(rentals.contains(rental2));
        assertTrue(rentals.contains(rental3));
    }

    @Test
    void givenValidRental_whenCreatingRental_thenCreateRental(){
        //given
        GameModel game = createAndSaveGame("xadrez", "image.test", 3, 1000);
        CustomerModel customer = createAndSaveCustomer("Test", "01234567890");

        RentalDto rental = new RentalDto(customer.getId(), game.getId(), 3);
        HttpEntity<RentalDto> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<RentalModel> response = restTemplate.exchange(
            RENTAL_ENDPOINT,
            HttpMethod.POST,
            body,
            RentalModel.class
            );
    
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gameRepository.count());
    }

    @Test
    void givenGameNotStock_whenCreatingRental_thenThrowsErrorUnprocessableEntity(){
        //given
        GameModel game = createAndSaveGame("xadrez", "image.test", 1, 1000);
        CustomerModel customer = createAndSaveCustomer("TestA", "01234567890");
        createAndSaveRental(3, customer, game);
        
        RentalDto rentalConflict = new RentalDto(customer.getId(), game.getId(), 3);
        HttpEntity<RentalDto> body = new HttpEntity<>(rentalConflict);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            RENTAL_ENDPOINT,
            HttpMethod.POST,
            body,
            String.class
            );
    
        //then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(1, rentalRepository.count());

    }

    @SuppressWarnings("null")
    @Test
    void givenInvalidCustomerIdOrGameId_whenCreatingRental_thenThrowsErrorNotFound(){
        //given
        CustomerModel customer = createAndSaveCustomer("TestA", "01234567890");
        customerRepository.deleteById(customer.getId());
        GameModel game = createAndSaveGame("xadrez", "image.test", 3, 1000);
        gameRepository.deleteById(game.getId());

        RentalDto rental = new RentalDto(customer.getId(), game.getId(), 3);
        HttpEntity<RentalDto> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            RENTAL_ENDPOINT,
            HttpMethod.POST,
            body,
            String.class 
            );
        
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    void givenInvalidDTO_whenCreatingRental_thenThrowsErrorBadRequest(){
        //given
        RentalDto rental = new RentalDto(null, null, 0);
        HttpEntity<RentalDto> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
            RENTAL_ENDPOINT,
            HttpMethod.POST,
            body,
            String.class
            );
        
        //then
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    
    //helper methods
    private GameModel createAndSaveGame (String name, String image, int stockTotal, int pricePerDay){
		GameModel game = new GameModel(null, name, image, stockTotal, pricePerDay);
		return gameRepository.save(game);
	};

    private CustomerModel createAndSaveCustomer (String name, String cpf){
        CustomerModel customer = new CustomerModel(null, name, cpf);
        return customerRepository.save(customer);
    };

    private RentalModel createAndSaveRental (int daysRented, CustomerModel customer, GameModel game){
        LocalDate rentDate = LocalDate.now();
        int originalPrice = daysRented * game.getPricePerDay();
        RentalModel rental = new RentalModel(
            null,
            rentDate, 
            daysRented, 
            null, 
            originalPrice, 
            0, customer, game
        );
        return rentalRepository.save(rental);
    }
}

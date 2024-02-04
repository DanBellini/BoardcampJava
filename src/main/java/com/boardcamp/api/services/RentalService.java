package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDto;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.RentalNotAvailableStockException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {
    final CustomerRepository customerRepository;
    final GameRepository gameRepository;
    final RentalRepository rentalRepository;


    RentalService(CustomerRepository customerRepository,
                  GameRepository gameRepository,
                  RentalRepository rentalRepository){
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;        
        this.rentalRepository = rentalRepository;
    }

    @SuppressWarnings("null")
    public RentalModel save (RentalDto dto){

        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
            ()-> new CustomerNotFoundException("Customer not found by this CustomerId!")
        );

        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
            () -> new GameNotFoundException("Game not found by this gameId!")
        );

        int rentedStock = rentalRepository.countByGame(game);
        int availableStock = game.getStockTotal();

        if(rentedStock >= availableStock){
            throw new RentalNotAvailableStockException("Insufficient stock for this game!");
        }

        int originalPrice = dto.getDaysRented() * game.getPricePerDay();

        RentalModel rental = new RentalModel(dto, customer, game, originalPrice);
        return rentalRepository.save(rental);
    }
}

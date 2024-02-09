package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDto;
import com.boardcamp.api.exceptions.customer_errors.CustomerNotFoundException;
import com.boardcamp.api.exceptions.game_errors.GameNotFoundException;
import com.boardcamp.api.exceptions.rental_errors.RentalAlreadyReturnedException;
import com.boardcamp.api.exceptions.rental_errors.RentalNotAvailableStockException;
import com.boardcamp.api.exceptions.rental_errors.RentalNotFoundException;
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

    public List<RentalModel> findAll(){
        return rentalRepository.findAllOrderByIdDesc();
    }

    @SuppressWarnings("null")
    public RentalModel save (RentalDto dto){
        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
            ()-> new CustomerNotFoundException("Customer not found by this CustomerId!")
        );

        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
            () -> new GameNotFoundException("Game not found by this gameId!")
        );

        int rentedStock = rentalRepository.countByGameNotReturn(game.getId());
        int availableStock = game.getStockTotal();

        if(rentedStock >= availableStock){
            throw new RentalNotAvailableStockException("Insufficient stock for this game!");
        }

        int originalPrice = dto.getDaysRented() * game.getPricePerDay();

        RentalModel rental = new RentalModel(dto, customer, game, originalPrice);
        return rentalRepository.save(rental);
    }

    private int calculateDelayFee (LocalDate rentDate, int daysRented, int pricePerDay){
        LocalDate currentDate = LocalDate.now();
        Long periodRental = ChronoUnit.DAYS.between(rentDate, currentDate); 

        if(periodRental <= daysRented){
            return 0;
        } else{
            return (int) ((periodRental - daysRented) * pricePerDay);  
        }
    }

    @SuppressWarnings("null")
    public RentalModel update (Long id){
        RentalModel rental = rentalRepository.findById(id).orElseThrow(
            () -> new RentalNotFoundException("Rental not found by this gameId!")
        );

        if(rental.getReturnDate() != null){
            throw new RentalAlreadyReturnedException("This rent has already been refunded");
        }

        int delayFee = calculateDelayFee(rental.getRentDate(),rental.getDaysRented(), rental.getOriginalPrice());

        RentalModel newRental = new RentalModel(rental, delayFee);
        newRental.setId(id);
        return rentalRepository.save(newRental);
    }
}

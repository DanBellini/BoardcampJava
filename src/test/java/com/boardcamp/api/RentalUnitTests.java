package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.exceptions.rental_errors.RentalAlreadyReturnedException;
import com.boardcamp.api.exceptions.rental_errors.RentalNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;
import com.boardcamp.api.services.RentalService;

@SpringBootTest
class RentalUnitTests {
    
    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GameRepository gameRepository;

    @SuppressWarnings("null")
    @Test
    void givenInvalidRentalId_whenReturnRental_thenThrowsErrorNotFound(){
        //given
        when(rentalRepository.findById(any())).thenReturn(Optional.empty());    
        
        //when
        RentalNotFoundException exception = assertThrows(
            RentalNotFoundException.class, 
            () -> rentalService.update(null)
        );

        //then
        assertNotNull(exception);
        assertEquals("Rental not found by this RentalId!", exception.getMessage());
        verify(rentalRepository, times(1)).findById(any());
        verify(rentalRepository, times(0)).save(any());
    }

    @SuppressWarnings("null")
    @Test
    void givenARentalIdAlreadyReturned_whenReturnRental_thenThrowsErrorRentalAlreadyReturned(){
        //given
        GameModel game = new GameModel(1L, "Test.name", "test.image", 3, 1000);
        CustomerModel customer = new CustomerModel(1L, "TestA", "01234567890");
        RentalModel rental = new RentalModel(
            1L, 
            LocalDate.now().minusDays(2), 
            1, 
            LocalDate.now().minusDays(1), 
            1000, 
            0, 
            customer, 
            game
            );
        doReturn(Optional.of(rental)).when(rentalRepository).findById(any());
        
        //when
        RentalAlreadyReturnedException exception = assertThrows(
            RentalAlreadyReturnedException.class,
            () -> rentalService.update(rental.getId())
            );
        //then
        assertNotNull(exception);
        assertEquals("This rent has already been refunded", exception.getMessage());
        verify(rentalRepository, times(1)).findById(any());
        verify(rentalRepository, times(0)).save(any());
    }

        @SuppressWarnings("null")
    @Test
    void givenARentalIdValid_whenReturnRental_thenUpdateRental(){
        //given
        LocalDate now = LocalDate.now();
        Long rentalId = 1l;
        GameModel game = new GameModel(1L, "Test.name", "test.image", 3, 1000);
        CustomerModel customer = new CustomerModel(1L, "TestA", "01234567890");
        RentalModel rental = new RentalModel(
            rentalId, 
            LocalDate.now().minusDays(2), 
            2, 
            null, 
            2000, 
            0, 
            customer, 
            game
            );

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        int delayFee = calculateDelayFee(rental.getRentDate(), rental.getDaysRented(), rental.getOriginalPrice());
        RentalModel newRental = new RentalModel(rental, delayFee);
        newRental.setId(rentalId);
        when(rentalRepository.save(any(RentalModel.class))).thenReturn(newRental);
        //when
        RentalModel result = rentalService.update(rentalId);

        //then
        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        assertNotNull(result.getReturnDate());
        assertEquals(now, result.getReturnDate());
        verify(rentalRepository, times(1)).findById(any());
        verify(rentalRepository, times(1)).save(any());
    }

    @SuppressWarnings("null")
    @Test
    void givenALateRent_whenReturnRental_thenUpdateRentalAndDelayFee(){
        //given
        LocalDate now = LocalDate.now();
        Long rentalId = 1l;
        GameModel game = new GameModel(1L, "Test.name", "test.image", 3, 1000);
        CustomerModel customer = new CustomerModel(1L, "TestA", "01234567890");
        RentalModel rental = new RentalModel(
            rentalId, 
            LocalDate.now().minusDays(4), 
            2, 
            null, 
            2000, 
            0, 
            customer, 
            game
            );

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        int delayFee = calculateDelayFee(rental.getRentDate(), rental.getDaysRented(), rental.getOriginalPrice());
        RentalModel newRental = new RentalModel(rental, delayFee);
        newRental.setId(rentalId);
        when(rentalRepository.save(any(RentalModel.class))).thenReturn(newRental);
        //when
        RentalModel result = rentalService.update(rentalId);

        //then
        assertNotNull(result);
        assertEquals(rentalId, result.getId());
        assertNotNull(result.getReturnDate());
        assertEquals(now, result.getReturnDate());
        assertEquals(delayFee, result.getDelayFee());
        verify(rentalRepository, times(1)).findById(any());
        verify(rentalRepository, times(1)).save(any());
    }

    //helper methods
    private int calculateDelayFee (LocalDate rentDate, int daysRented, int originalPrice){
        LocalDate currentDate = LocalDate.now();
        Long periodRental = ChronoUnit.DAYS.between(rentDate, currentDate);
        int pricePerDay = originalPrice / daysRented;

        if(periodRental <= daysRented){
            return 0;
        } else{
            return (int) ((periodRental - daysRented) * pricePerDay);  
        }
    }
}

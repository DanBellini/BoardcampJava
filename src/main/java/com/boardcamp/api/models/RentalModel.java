package com.boardcamp.api.models;

import java.time.LocalDate;

import com.boardcamp.api.dtos.RentalDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Rentals")
public class RentalModel {

    public RentalModel (RentalDto dto,
                        CustomerModel customer,
                        GameModel game,  
                        int originalPrice){
        this.customer = customer;
        this.game = game;
        this.daysRented = dto.getDaysRented();
        this.originalPrice = originalPrice;
        this.rentDate = LocalDate.now();
        this.returnDate = null;
        this.delayFee = 0;
    }

    public RentalModel (RentalModel rental, int delayFee){
        this.customer = rental.getCustomer();
        this.game = rental.getGame();
        this.daysRented = rental.getDaysRented();
        this.originalPrice = rental.getOriginalPrice();
        this.rentDate = rental.getRentDate();
        this.returnDate = LocalDate.now();
        this.delayFee = delayFee;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private LocalDate rentDate;

    @Column(nullable = false)
    private int daysRented;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false)
    private int delayFee;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "game")
    private GameModel game;
}

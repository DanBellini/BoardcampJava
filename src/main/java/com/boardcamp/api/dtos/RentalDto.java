package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalDto {

    @NotNull
    private Long customerId;

    @NotNull
    private Long gameId;
    
    @NotNull
    @Min(value = 1, message = "Rented Days must be greater than 1! ")
    private int daysRented;
}

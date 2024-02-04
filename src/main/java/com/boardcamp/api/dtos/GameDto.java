package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDto {

    @NotBlank
    @Size(max = 150, message = "Maximum length for name is 150 characters!")
    private String name;

    @NotBlank
    private String image;

    @NotNull
    @Min(value = 1, message = "Stock Total must not be negative or 0")
    private int stockTotal;

    @NotNull
    @Min(value = 1, message = "The price per day must not be negative or 0")
    private int pricePerDay;
}

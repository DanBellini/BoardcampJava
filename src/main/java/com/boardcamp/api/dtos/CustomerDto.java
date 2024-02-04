package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    
    @NotBlank
    @Size(max = 150, message = "Maximun length for name is 150 characters!")
    private String name;

    @NotBlank
    @Size(min = 11, max = 11, message = "The CPF must be a string with 11 characters!")
    private String cpf;
}

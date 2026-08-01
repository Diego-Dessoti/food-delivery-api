package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(
        @NotBlank(message = "A rua é obrigatória")
        String street,
        @NotBlank(message = "O número é obrigatória")
        String number,
        @NotBlank(message = "A cidade é obrigatória")
        String city,
        @NotBlank(message = "O bairro é obrigatória")
        String neighborhood,
        @NotBlank(message = "O estado é obrigatória")
        String state,
        @NotBlank(message = "O CEP é obrigatória")
        String zipCode,
        @Size(max = 100)
        String complement
) {
}

package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatória")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String name,

        @NotBlank(message = "A descrição do produto é obrigatória")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(
                value = "0.01",
                message = "O preço deve ser maior que zero"
        )
        BigDecimal price,

        @NotNull(message = "O campo ativo é obrigatório")
        Boolean active

) {
}

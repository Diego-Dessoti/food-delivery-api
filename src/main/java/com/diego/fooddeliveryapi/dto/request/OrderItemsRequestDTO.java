package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record OrderItemsRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório")
        String productName,

        @NotNull(message = "A quantidade é obrigatório")
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        Integer quantity,

        @NotNull(message = "O valor da unidade é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor da unidade é obrigatório")
        BigDecimal unitValue

) {
}

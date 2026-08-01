package com.diego.fooddeliveryapi.dto.request;

import com.diego.fooddeliveryapi.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateOrderRequestDTO(
        @NotNull(message = "O campo de status é obrigatório")
        OrderStatus status
) {
}

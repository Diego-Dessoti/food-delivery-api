package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequestDTO(
        @NotBlank(message = "O nome do cliente é obrigatório")
        String customerName,

        @NotNull(message = "A loja é obrigatória")
        Long storeId,

        @NotNull(message = "O endereço é obrigatório")
        @Valid
        AddressRequestDTO deliveryAddress,

        @NotEmpty(message = "O pedido deve possuir pelo menos um item")
        List<@Valid OrderItemsRequestDTO> items

) {
}

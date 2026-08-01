package com.diego.fooddeliveryapi.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        String productName,
        Integer quantity,
        BigDecimal unitValue,
        BigDecimal subtotal

) {
}

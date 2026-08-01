package com.diego.fooddeliveryapi.dto.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean active
) {

}

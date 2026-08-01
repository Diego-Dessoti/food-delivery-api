package com.diego.fooddeliveryapi.dto.response;

public record StoreResponseDTO(
        Long id,
        String name,
        Boolean active,
        Long ownerId,
        String ownerName
) {
}

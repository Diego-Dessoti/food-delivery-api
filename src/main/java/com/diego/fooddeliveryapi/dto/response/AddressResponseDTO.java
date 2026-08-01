package com.diego.fooddeliveryapi.dto.response;

public record AddressResponseDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement
) {
}

package com.diego.fooddeliveryapi.dto.response;

import jakarta.validation.constraints.NotBlank;

public record LoginReponseDTO(
        Long id,
        String name,
        String email,
        String token
) {


}

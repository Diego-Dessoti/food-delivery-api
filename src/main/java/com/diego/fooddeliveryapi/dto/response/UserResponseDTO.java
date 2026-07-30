package com.diego.fooddeliveryapi.dto.response;

import com.diego.fooddeliveryapi.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResponseDTO(
        Long id,
        String name,
        String email
) {

}

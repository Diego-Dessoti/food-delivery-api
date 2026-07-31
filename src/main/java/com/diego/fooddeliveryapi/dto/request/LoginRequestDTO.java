package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "O campo email deve ser preenchido!")
        String email,

        @NotBlank(message = "O campo senha deve ser preenchido!")
        String password
) {


}

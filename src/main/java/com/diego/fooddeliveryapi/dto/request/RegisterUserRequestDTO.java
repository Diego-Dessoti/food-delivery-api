package com.diego.fooddeliveryapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequestDTO(
        @NotBlank(message = "O campo nome deve ser preenchido!")
        @Size(min = 3, max = 200, message = "O campo deve ter ao menos 3 caracteres")
        String name,

        @NotBlank(message = "O campo email deve ser preenchido!")
        String email,

        @NotBlank(message = "O campo senha deve ser preenchido!")
        String password
) {


}

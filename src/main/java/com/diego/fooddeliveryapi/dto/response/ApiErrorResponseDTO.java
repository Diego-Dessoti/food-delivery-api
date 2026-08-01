package com.diego.fooddeliveryapi.dto.response;

import java.time.LocalDateTime;

public record ApiErrorResponseDTO(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {

}

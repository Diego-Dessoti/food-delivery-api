package com.diego.fooddeliveryapi.exception;

import com.diego.fooddeliveryapi.dto.response.ApiErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiErrorResponseDTO apiErrorResponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(apiErrorResponseDTO);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleInvalidCredentials(
            InvalidCredentialsException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;


        ApiErrorResponseDTO apiErrorResponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(apiErrorResponseDTO);
    }

    @ExceptionHandler(OrderNotfoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleOrderNotFound(
            OrderNotfoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiErrorResponseDTO reponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(reponseDTO);
    }

    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleInvalidOrderStatusTransition(
            InvalidOrderStatusTransitionException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiErrorResponseDTO apiErrorResponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiErrorResponseDTO);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleProductNotFound(
            ProductNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiErrorResponseDTO response = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleInvalidOrderStatus(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorResponseDTO apiErrorResponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "Status inválido. Valores permitidos: RECEBIDO, EM_PREPARO, " +
                        "SAIU_PARA_ENTREGA, ENTREGUE e CANCELADO",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(apiErrorResponseDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiErrorResponseDTO reponseDTO = new ApiErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(reponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("message", "Existem campos inválidos");
        response.put("fields", fieldErrors);
        response.put("path", request.getRequestURI());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}

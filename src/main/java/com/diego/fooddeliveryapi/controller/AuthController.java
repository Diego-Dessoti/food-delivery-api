package com.diego.fooddeliveryapi.controller;

import com.diego.fooddeliveryapi.dto.request.RegisterRequestDTO;
import com.diego.fooddeliveryapi.dto.response.UserResponseDTO;
import com.diego.fooddeliveryapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        UserResponseDTO userResponseDTO = authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}

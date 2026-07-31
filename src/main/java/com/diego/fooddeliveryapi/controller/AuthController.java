package com.diego.fooddeliveryapi.controller;

import com.diego.fooddeliveryapi.dto.request.LoginRequestDTO;
import com.diego.fooddeliveryapi.dto.request.RegisterUserRequestDTO;
import com.diego.fooddeliveryapi.dto.response.LoginReponseDTO;
import com.diego.fooddeliveryapi.dto.response.UserResponseDTO;
import com.diego.fooddeliveryapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO dto) {
        UserResponseDTO userResponseDTO = authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginReponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginReponseDTO loginReponseDTO = authService.login(dto);

        return ResponseEntity.status(HttpStatus.OK).body(loginReponseDTO);
    }
}

package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.RegisterRequestDTO;
import com.diego.fooddeliveryapi.dto.response.UserResponseDTO;
import com.diego.fooddeliveryapi.entity.User;
import com.diego.fooddeliveryapi.exception.EmailAlreadyExistsException;
import com.diego.fooddeliveryapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO register(RegisterRequestDTO dto) {
        String normalizedEmail = dto.email().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setName(dto.name().trim());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}

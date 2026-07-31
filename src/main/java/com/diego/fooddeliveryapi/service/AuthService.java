package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.LoginRequestDTO;
import com.diego.fooddeliveryapi.dto.request.RegisterUserRequestDTO;
import com.diego.fooddeliveryapi.dto.response.LoginReponseDTO;
import com.diego.fooddeliveryapi.dto.response.UserResponseDTO;
import com.diego.fooddeliveryapi.entity.User;
import com.diego.fooddeliveryapi.exception.EmailAlreadyExistsException;
import com.diego.fooddeliveryapi.exception.InvalidCredentialsException;
import com.diego.fooddeliveryapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponseDTO register(RegisterUserRequestDTO dto) {
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

    public LoginReponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordEncoder.matches(dto.password(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return
                new LoginReponseDTO(user.getId(), user.getName(), user.getEmail(), token);
    }
}

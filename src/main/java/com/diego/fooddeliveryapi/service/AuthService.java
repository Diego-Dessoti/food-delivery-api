package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.LoginRequestDTO;
import com.diego.fooddeliveryapi.dto.request.RegisterUserRequestDTO;
import com.diego.fooddeliveryapi.dto.response.LoginReponseDTO;
import com.diego.fooddeliveryapi.dto.response.UserResponseDTO;
import com.diego.fooddeliveryapi.entity.Store;
import com.diego.fooddeliveryapi.entity.User;
import com.diego.fooddeliveryapi.enums.UserRole;
import com.diego.fooddeliveryapi.exception.EmailAlreadyExistsException;
import com.diego.fooddeliveryapi.exception.InvalidCredentialsException;
import com.diego.fooddeliveryapi.repository.StoreRepository;
import com.diego.fooddeliveryapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, StoreRepository storeRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
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
        user.setRole(dto.role());
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        if (user.getRole() == UserRole.RESTAURANT) {
            Store store = new Store();
            store.setName(user.getName());
            store.setActive(true);
            store.setOwner(user);
            storeRepository.save(store);
        }

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public LoginReponseDTO login(LoginRequestDTO dto) {
        String normalizedEmail = dto.email().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordEncoder.matches(dto.password(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return
                new LoginReponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole().toString(), token);
    }
}

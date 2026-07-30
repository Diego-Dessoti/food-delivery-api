package com.diego.fooddeliveryapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/auth/register",
                                        "/h2-console/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .headers(headers -> headers.
                        frameOptions(frame -> frame.sameOrigin())
                )
                .build();
    }
}

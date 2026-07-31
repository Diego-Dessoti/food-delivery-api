package com.diego.fooddeliveryapi.controller;

import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping
    public String authenticated(@AuthenticationPrincipal Jwt jwt) {
        return "Usuário autenticado: " + jwt.getSubject();
    }
}

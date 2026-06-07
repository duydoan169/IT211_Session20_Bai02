package org.example.bai2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.bai2.dto.AuthResponse;
import org.example.bai2.dto.LoginRequest;
import org.example.bai2.dto.RefreshTokenRequest;
import org.example.bai2.service.AuthService;

@RestController
@RequestMapping("/api/gallery/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public String logout() {
        authService.logout();
        return "Logout success";
    }
}
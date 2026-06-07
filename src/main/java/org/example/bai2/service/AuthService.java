package org.example.bai2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.bai2.dto.AuthResponse;
import org.example.bai2.dto.LoginRequest;
import org.example.bai2.dto.RefreshTokenRequest;
import org.example.bai2.entity.Account;
import org.example.bai2.entity.TokenSession;
import org.example.bai2.repository.AccountRepository;
import org.example.bai2.repository.TokenSessionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final TokenSessionRepository tokenSessionRepository;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(account);
        String refreshToken = jwtService.generateRefreshToken(account);
        TokenSession tokenSession = TokenSession.builder().refreshTokenValue(refreshToken).revoked(false).expired(false).account(account).build();
        tokenSessionRepository.save(tokenSession);
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        TokenSession session = tokenSessionRepository.findByRefreshTokenValue(request.getRefreshToken()).orElseThrow();
        if (session.isExpired() || session.isRevoked()) {
            throw new RuntimeException("Refresh token invalid");
        }
        Account account = session.getAccount();
        String newAccessToken = jwtService.generateAccessToken(account);
        return AuthResponse.builder().accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken()).build();
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountRepository.findByUsername(username).orElseThrow();
        List<TokenSession> sessions = tokenSessionRepository.findAllByAccountId(account.getId());
        sessions.stream().filter(session -> !session.isExpired() && !session.isRevoked()).forEach(session -> {
            session.setExpired(true);
            session.setRevoked(true);
        });

        tokenSessionRepository.saveAll(sessions);
    }
}
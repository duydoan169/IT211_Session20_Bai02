package org.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.bai2.entity.TokenSession;

import java.util.List;
import java.util.Optional;

public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {

    Optional<TokenSession> findByRefreshTokenValue(String token);

    List<TokenSession> findAllByAccountId(Long accountId);
}
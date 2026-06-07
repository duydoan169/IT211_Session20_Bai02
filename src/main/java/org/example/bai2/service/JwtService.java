package org.example.bai2.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.example.bai2.entity.Account;
import org.example.bai2.entity.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateAccessToken(Account account) {
        List<String> roles = account.getRoles().stream().map(Role::getRoleName).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder().setClaims(claims).setSubject(account.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + accessExpiration)).signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }
    public String generateRefreshToken(Account account) {

        return Jwts.builder().setSubject(account.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + refreshExpiration)).signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }
    public String extractUsername(String token) {

        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
package com.example.financeData.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    private final String SECRET_KEY = "my-super-secret-key-that-is-at-least-32-bytes!";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Long userId, String role){
        Instant now= Instant.now();
        Key key= getSigningKey();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role",role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(60*60)))
                .signWith(key)
                .compact();
    }

    public Long extractUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}


package com.example.hotel_service.config;

import io.jsonwebtoken.Claims;
import lombok.Value;

import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(
                extractClaims(token).getSubject()
        );
    }

    public String extractRole(String token) {
        return extractClaims(token)
                .get("role", String.class);
    }
}

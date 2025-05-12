package com.ortoroverbasso.ortorovebasso.utils;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Autowired
    UserRepository userRepository;

    @Value("${app.jwt-secret}")
    private String secretKeyBase64; // Deve essere base64 encoded

    private SecretKey getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    // Metodo per generare il token JWT
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 ore
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    // Estrai userId dal token
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    // Valida il token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromEmail(String email) {
        // Se hai un UserRepository, puoi fare:
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return user.getId();
    }
}

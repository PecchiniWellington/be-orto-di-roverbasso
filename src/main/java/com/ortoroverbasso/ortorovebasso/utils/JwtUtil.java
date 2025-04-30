package com.ortoroverbasso.ortorovebasso.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private String secretKey = "yourSecretKey"; // Set a strong secret key

    // Method to generate a JWT token
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // Set the userId as the subject (converted to String)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Method to extract userId from JWT token
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getSubject()); // Convert the subject to Long
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token); // If token is valid, no exception will be thrown
            return true;
        } catch (Exception e) {
            return false; // Invalid token
        }
    }
}

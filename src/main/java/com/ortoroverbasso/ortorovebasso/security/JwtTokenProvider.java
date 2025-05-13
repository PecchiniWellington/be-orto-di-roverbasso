package com.ortoroverbasso.ortorovebasso.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds:86400000}") // 24 hours default
    private long jwtExpirationInMs;

    /**
     * Removes the "Bearer " prefix from a token if present
     */
    private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            logger.debug("Removing Bearer prefix from token");
            return token.substring(7);
        }
        return token;
    }

    /**
     * Creates a signing key from the JWT secret
     */
    private SecretKey getSigningKey() {
        System.out.println("[JWT DEBUG] jwtSecret (raw): " + jwtSecret);
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("[JWT DEBUG] Signing key generated: " + key);
        return key;
    }

    /**
     * Generate token from UserEntity
     */
    public String generateToken(UserEntity user) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("name", user.getName() != null ? user.getName() : "")
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    /**
     * Generate token from Authentication
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        token = cleanToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUserEmailFromToken(String token) {
        token = cleanToken(token);
        return getUsernameFromJWT(token);
    }

    public Long getUserIdFromToken(String token) {
        token = cleanToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    public String getRoleFromToken(String token) {
        token = cleanToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    public String getNameFromToken(String token) {
        token = cleanToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("name", String.class);
    }

    public boolean validateToken(String token) {
        System.out.println("[JWT DEBUG] Validating token: " + token);
        try {
            // Clean the token before validation
            token = cleanToken(token);
            System.out.println("[JWT DEBUG] Cleaned token for validation: " + token);

            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            System.out.println("[JWT DEBUG] Token is valid.");
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
        } catch (Exception ex) {
            logger.error("JWT token error: {}", ex.getMessage());
        }
        return false;
    }

    public Claims getAllClaimsFromToken(String token) {
        token = cleanToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDate(String token) {
        token = cleanToken(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public Date getExpirationDateFromToken(String token) {
        token = cleanToken(token);
        return getExpirationDate(token);
    }

    public boolean isTokenExpired(String token) {
        token = cleanToken(token);
        Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }
}

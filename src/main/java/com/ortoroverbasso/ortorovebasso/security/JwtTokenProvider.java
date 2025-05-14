package com.ortoroverbasso.ortorovebasso.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.jwt-expiration-milliseconds:86400000}")
    private long jwtExpirationInMs;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            logger.debug("Rimozione prefisso Bearer dal token");
            return token.substring(7);
        }
        return token;
    }

    public String generateToken(UserEntity user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("name", user.getName() != null ? user.getName() : "")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public String getUserEmailFromToken(String token) {
        return getUsernameFromToken(token);
    }

    public String getUsernameFromToken(String token) {
        token = cleanToken(token);
        return getAllClaimsFromToken(token).getSubject();
    }

    public Long getUserIdFromToken(String token) {
        token = cleanToken(token);
        return getAllClaimsFromToken(token).get("userId", Long.class);
    }

    public String getRoleFromToken(String token) {
        token = cleanToken(token);
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    public String getNameFromToken(String token) {
        token = cleanToken(token);
        return getAllClaimsFromToken(token).get("name", String.class);
    }

    public Claims getAllClaimsFromToken(String token) {
        token = cleanToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        token = cleanToken(token);
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            token = cleanToken(token);
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            logger.debug("Token JWT valido.");
            return true;
        } catch (SignatureException ex) {
            logger.error("Firma non valida: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Token malformato: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Token scaduto: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token non supportato: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Token vuoto o nullo: {}", ex.getMessage());
        } catch (Exception ex) {
            logger.error("Errore generico sul token JWT: {}", ex.getMessage());
        }
        return false;
    }
}

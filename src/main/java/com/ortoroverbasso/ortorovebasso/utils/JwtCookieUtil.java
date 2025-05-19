package com.ortoroverbasso.ortorovebasso.utils;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieUtil {

    @Value("${app.jwt-cookie-secure}")
    private boolean isSecure;

    public ResponseCookie createJwtCookie(String token) {
        return ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearJwtCookie() {
        return ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearCartTokenCookie() {
        return ResponseCookie.from("cartToken", "")
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

}

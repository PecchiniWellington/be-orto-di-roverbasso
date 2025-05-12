package com.ortoroverbasso.ortorovebasso.utils;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieUtil {

    public ResponseCookie createJwtCookie(String token, boolean isSecure) {
        return ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearJwtCookie(boolean isSecure) {
        return ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}

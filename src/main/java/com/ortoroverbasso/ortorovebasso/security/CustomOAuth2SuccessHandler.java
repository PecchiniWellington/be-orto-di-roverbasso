package com.ortoroverbasso.ortorovebasso.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.service.auth.impl.OAuth2Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2Service userProcessingService;

    public CustomOAuth2SuccessHandler(OAuth2Service userProcessingService) {
        this.userProcessingService = userProcessingService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String uri = request.getRequestURI();
        String provider = uri.contains("google") ? "GOOGLE"
                : uri.contains("facebook") ? "FACEBOOK"
                        : "OAUTH2";

        String token = userProcessingService.handleOAuth2User(attributes, provider);

        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:3000");
    }
}

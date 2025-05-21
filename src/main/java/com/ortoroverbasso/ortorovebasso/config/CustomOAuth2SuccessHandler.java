package com.ortoroverbasso.ortorovebasso.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public CustomOAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String rawEmail = (String) attributes.get("email");
        if (rawEmail == null || rawEmail.isBlank()) {
            rawEmail = "fbuser_" + attributes.get("id") + "@facebook.local";
        }
        final String email = rawEmail;

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(email);
                    newUser.setName((String) attributes.get("name"));
                    newUser.setPassword("");
                    newUser.setRole(Role.USER);
                    return userRepository.save(newUser);
                });

        String token = jwtTokenProvider.generateToken(user);

        // ✅ Elimina la sessione per evitare JSESSIONID
        request.getSession(false);
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(jwtCookie);

        System.out.println("✅ Login OAuth2 completato per utente: {}" + email);

        response.sendRedirect("http://localhost:3000");
    }
}

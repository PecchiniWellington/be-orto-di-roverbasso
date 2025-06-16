package com.ortoroverbasso.ortorovebasso.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IImagesDetailService imageDetailService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture"); // Avatar dal provider (es. Google)

        if (email == null || name == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email o nome non trovati nei dati OAuth");
            return;
        }

        // Trova o crea utente
        UserEntity user = userService.findOrCreateFromGoogle(email, name, picture);

        System.out.println("User found or created: " + user.getEmail());
        System.out.println("User Name: " + user.getName());

        userService.assignProviderAvatarIfMissing(user, picture);

        String token = jwtTokenProvider.generateToken(user);

        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // true in produzione
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(jwtCookie);

        response.sendRedirect("http://localhost:1");
    }

}

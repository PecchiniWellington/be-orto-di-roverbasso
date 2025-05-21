package com.ortoroverbasso.ortorovebasso.controller.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/google/success")
    public ResponseEntity<?> success(OAuth2AuthenticationToken authentication) {
        OAuth2User oauthUser = authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if (email == null || name == null) {
            return ResponseEntity.badRequest().body("Dati Google mancanti: email o nome non trovati");
        }

        try {
            // Cerca o crea l’utente nel DB
            UserEntity user = userService.findOrCreateFromGoogle(email, name);

            // Genera token JWT
            String token = jwtTokenProvider.generateToken(user);

            // Ritorna il token e le info utente
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", user.getEmail(),
                    "name", user.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Errore durante il login con Google");
        }
    }
}

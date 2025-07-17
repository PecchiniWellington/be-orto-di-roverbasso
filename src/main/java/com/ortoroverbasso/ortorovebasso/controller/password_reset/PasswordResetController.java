package com.ortoroverbasso.ortorovebasso.controller.password_reset;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.password_reset.PasswordChangeRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.password_reset.PasswordResetRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.password_reset.IPasswordResetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final IPasswordResetService passwordResetService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetController(IPasswordResetService passwordResetService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.passwordResetService = passwordResetService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody Map<String, String> request) {
        String email = request.get("email");
        passwordResetService.sendResetToken(email);
        return ResponseEntity.ok("Token inviato via email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetRequestDto request) {
        if (!passwordResetService.isValidToken(request.getToken())) {
            return ResponseEntity.badRequest().body("Token non valido o scaduto");
        }

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        passwordResetService.invalidateToken(request.getToken());

        return ResponseEntity.ok("Password aggiornata");

    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<String> validateResetToken(@RequestParam String token) {
        boolean valid = passwordResetService.isValidToken(token);
        if (!valid) {
            return ResponseEntity.badRequest().body("Token non valido o scaduto");
        }
        return ResponseEntity.ok("Token valido");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody PasswordChangeRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername(); // ottenuto dal token

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("La vecchia password non è corretta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password aggiornata con successo");
    }
}

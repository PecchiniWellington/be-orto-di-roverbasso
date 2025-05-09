package com.ortoroverbasso.ortorovebasso.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.auth.LoginRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICartService cartService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        System.out.println("[LOGIN DEBUG] Tentativo di login per: " + loginDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        System.out.println("[LOGIN DEBUG] Utente trovato: id=" + user.getId() + ", email=" + user.getEmail());

        String token = tokenProvider.generateToken(user);
        System.out.println("[LOGIN DEBUG] Token generato: " + token);

        // ✅ Associa carrello se non esiste
        cartService.getCart(user.getId());

        return ResponseEntity.ok(new JwtAuthResponseDto(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                tokenProvider.getExpirationDateFromToken(token)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto dto) {
        System.out.println("[REGISTER DEBUG] Tentativo di registrazione per: " + dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            System.out.println("[REGISTER DEBUG] Email già registrata: " + dto.getEmail());
            return ResponseEntity.badRequest().body("Email già registrata");
        }

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);

        userRepository.save(user);
        System.out.println("[REGISTER DEBUG] Registrazione completata per userId=" + user.getId());

        return ResponseEntity.ok("Utente registrato con successo");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        System.out.println("[LOGOUT DEBUG] Logout effettuato, contesto di sicurezza pulito.");
        return ResponseEntity.ok("Logout effettuato con successo");
    }
}

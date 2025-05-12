package com.ortoroverbasso.ortorovebasso.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.auth.LoginRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;
import com.ortoroverbasso.ortorovebasso.utils.EnvironmentConfig;
import com.ortoroverbasso.ortorovebasso.utils.JwtCookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private JwtCookieUtil jwtCookieUtil;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(
            @RequestBody LoginRequestDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
            String token = tokenProvider.generateToken(user);

            // Recupera cartToken guest da cookie (se esiste)
            String guestCartToken = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("cartToken".equals(cookie.getName())) {
                        guestCartToken = cookie.getValue();
                        break;
                    }
                }
            }

            // Prova a fare il merge solo se guest cart esiste
            if (guestCartToken != null && !guestCartToken.isBlank()) {
                try {
                    if (cartService.existsByCartToken(guestCartToken)) {
                        cartService.mergeCarts(user.getId(), guestCartToken);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // logga ma continua
                }
            }

            // ✅ Ottieni o crea carrello utente
            CartResponseDto userCart = cartService.getCart(user.getId());
            String userCartToken = userCart.getCartToken();

            // ✅ Imposta nuovo cartToken (autenticato)
            Cookie cartCookie = new Cookie("cartToken", userCartToken);
            cartCookie.setHttpOnly(true);
            cartCookie.setSecure(environmentConfig.isProduction());
            cartCookie.setPath("/");
            cartCookie.setMaxAge(60 * 60 * 24 * 7); // 7 giorni
            response.addCookie(cartCookie);

            // ✅ Imposta cookie JWT
            ResponseCookie jwtCookie = jwtCookieUtil.createJwtCookie(token, environmentConfig.isProduction());
            response.addHeader("Set-Cookie", jwtCookie.toString());

            return ResponseEntity.ok(new JwtAuthResponseDto(
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getRole().name(),
                    tokenProvider.getExpirationDateFromToken(token)));

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
    public ResponseEntity<?> logout(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        System.out.println("[LOGOUT DEBUG] Logout effettuato, contesto di sicurezza pulito.");

        // 🔐 Rimuovi cookie JWT
        ResponseCookie jwtCookie = jwtCookieUtil.clearJwtCookie(environmentConfig.isProduction());
        response.addHeader("Set-Cookie", jwtCookie.toString());

        // 🔐 Rimuovi cookie cartToken
        ResponseCookie clearCartTokenCookie = ResponseCookie.from("cartToken", "")
                .httpOnly(true)
                .secure(environmentConfig.isProduction())
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", clearCartTokenCookie.toString());

        return ResponseEntity.ok("Logout effettuato con successo");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkCurrentUser() {
        try {
            System.out.println("[AUTH CHECK] Checking current authenticated user");
            return userService.getCurrentAuthenticatedUser();
        } catch (Exception e) {
            System.out.println("[AUTH CHECK] Error in check endpoint: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking authentication: " + e.getMessage());
        }
    }
}

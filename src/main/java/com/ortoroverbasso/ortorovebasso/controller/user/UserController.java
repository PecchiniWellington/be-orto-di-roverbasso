// UserController.java
package com.ortoroverbasso.ortorovebasso.controller.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.config.EnvironmentConfig;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.email.IEmailVerificationService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;
import com.ortoroverbasso.ortorovebasso.utils.JwtCookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtCookieUtil jwtCookieUtil;
    @Autowired
    private EnvironmentConfig environmentConfig;
    @Autowired
    private ICartService cartService;

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        return userService.updateUser(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletResponse response) {
        userService.deleteUser(id);

        // 🔴 Cancella cookie JWT e cartToken
        ResponseCookie jwtCookie = jwtCookieUtil.clearJwtCookie();
        response.addHeader("Set-Cookie", jwtCookie.toString());

        ResponseCookie cartTokenCookie = jwtCookieUtil.clearCartTokenCookie();
        response.addHeader("Set-Cookie", cartTokenCookie.toString());

        // 🔄 Imposta un nuovo cartToken da guest
        String newGuestCartToken = UUID.randomUUID().toString();
        cartService.createCartWithToken(newGuestCartToken);

        Cookie newCartCookie = new Cookie("cartToken", newGuestCartToken);
        newCartCookie.setHttpOnly(true);
        newCartCookie.setSecure(environmentConfig.isProduction());
        newCartCookie.setPath("/");
        newCartCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(newCartCookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentAuthenticatedUser(@AuthenticationPrincipal OAuth2User principal) {
        try {
            if (principal != null && principal.getAttributes() != null) {
                String email = (String) principal.getAttributes().get("email");
                return userService.getCurrentAuthenticatedUserByEmail(email);
            }
            return userService.getCurrentAuthenticatedUser();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking authentication: " + e.getMessage());
        }
    }

    @Autowired
    private IEmailVerificationService emailVerificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        boolean success = emailVerificationService.verifyEmail(token);
        if (success) {
            return ResponseEntity.ok("Email verificata con successo");
        } else {
            return ResponseEntity.badRequest().body("Token non valido o scaduto");
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> resendVerificationEmail(Authentication authentication) {
        String userEmail = authentication.getName(); // email dall’utente autenticato

        UserEntity user = userService.findByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non trovato");
        }

        if (user.isEmailVerified()) {
            return ResponseEntity.badRequest().body("Email già verificata");
        }

        emailVerificationService.sendVerificationToken(user);
        return ResponseEntity.ok("Email di verifica inviata");
    }
}

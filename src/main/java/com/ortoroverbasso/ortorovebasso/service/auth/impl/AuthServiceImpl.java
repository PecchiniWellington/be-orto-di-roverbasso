package com.ortoroverbasso.ortorovebasso.service.auth.impl;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.auth.JwtAuthResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.auth.LoginRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.user.AccountStatus;
import com.ortoroverbasso.ortorovebasso.entity.user.Role;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_profile.UserProfileEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.security.JwtTokenProvider;
import com.ortoroverbasso.ortorovebasso.service.auth.IAuthService;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.utils.EnvironmentConfig;
import com.ortoroverbasso.ortorovebasso.utils.JwtCookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements IAuthService {

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

    @Override
    public ResponseEntity<JwtAuthResponseDto> login(LoginRequestDto loginDto, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        String token = tokenProvider.generateToken(user);

        String guestCartToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cartToken".equals(cookie.getName())) {
                    guestCartToken = cookie.getValue();
                    break;
                }
            }
        }

        if (guestCartToken != null && !guestCartToken.isBlank()) {
            try {
                if (cartService.existsByCartToken(guestCartToken)) {
                    cartService.mergeCarts(user.getId(), guestCartToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CartResponseDto userCart = cartService.getCart(user.getId());
        String userCartToken = userCart.getCartToken();

        Cookie cartCookie = new Cookie("cartToken", userCartToken);
        cartCookie.setHttpOnly(true);
        cartCookie.setSecure(environmentConfig.isProduction());
        cartCookie.setPath("/");
        cartCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cartCookie);

        ResponseCookie jwtCookie = jwtCookieUtil.createJwtCookie(token, environmentConfig.isProduction());
        response.addHeader("Set-Cookie", jwtCookie.toString());

        return ResponseEntity.ok(new JwtAuthResponseDto(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                tokenProvider.getExpirationDateFromToken(token)));
    }

    @Override
    public ResponseEntity<?> register(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email già registrata");
        }

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);
        user.setAccountStatus(AccountStatus.ACTIVE);

        UserProfileEntity profile = new UserProfileEntity();
        profile.setUser(user);

        UserPreferencesEntity preferences = new UserPreferencesEntity();
        preferences.setUser(user);

        UserSecurityEntity security = new UserSecurityEntity();
        security.setUser(user);

        user.setProfile(profile);
        user.setPreferences(preferences);
        user.setSecurity(security);

        userRepository.save(user);

        return ResponseEntity.ok("Utente registrato con successo");
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        ResponseCookie jwtCookie = jwtCookieUtil.clearJwtCookie(environmentConfig.isProduction());
        response.addHeader("Set-Cookie", jwtCookie.toString());

        ResponseCookie clearCartTokenCookie = jwtCookieUtil.clearCartTokenCookie(environmentConfig.isProduction());
        response.addHeader("Set-Cookie", clearCartTokenCookie.toString());

        String newGuestCartToken = UUID.randomUUID().toString();

        cartService.createCartWithToken(newGuestCartToken);

        Cookie newCartCookie = new Cookie("cartToken", newGuestCartToken);
        newCartCookie.setHttpOnly(true);
        newCartCookie.setSecure(environmentConfig.isProduction());
        newCartCookie.setPath("/");
        newCartCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(newCartCookie);

        return ResponseEntity.ok(Map.of("message", "Logout effettuato con successo", "cartToken", newGuestCartToken));
    }

}

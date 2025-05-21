package com.ortoroverbasso.ortorovebasso.controller.user.user_security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.config.EnvironmentConfig;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserSecurityResponseDto;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.user.user_security.IUserSecurityService;
import com.ortoroverbasso.ortorovebasso.utils.JwtCookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users/{userId}/security")
public class UserSecurityController {

    @Autowired
    private IUserSecurityService securityService;
    @Autowired
    private JwtCookieUtil jwtCookieUtil;
    @Autowired
    private EnvironmentConfig environmentConfig;
    @Autowired
    private ICartService cartService;

    @PostMapping
    public ResponseEntity<UserSecurityResponseDto> create(@PathVariable Long userId,
            @RequestBody UserSecurityRequestDto dto) {
        return ResponseEntity.ok(securityService.create(userId, dto));
    }

    @PutMapping
    public ResponseEntity<UserSecurityResponseDto> update(@PathVariable Long userId,
            @RequestBody UserSecurityRequestDto dto) {
        return ResponseEntity.ok(securityService.update(userId, dto));
    }

    @GetMapping
    public ResponseEntity<UserSecurityResponseDto> get(@PathVariable Long userId) {
        return ResponseEntity.ok(securityService.getByUserId(userId));
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@PathVariable Long userId, HttpServletResponse response) {
        securityService.deleteByUserId(userId);

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
}

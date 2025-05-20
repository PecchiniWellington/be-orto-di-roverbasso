package com.ortoroverbasso.ortorovebasso.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.user.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    // ==== USER ENDPOINTS ====

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> getUserCart() {
        Long userId = getAuthenticatedUserId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> addItemToUserCart(@RequestBody CartRequestDto dto) {
        Long userId = getAuthenticatedUserId();
        return ResponseEntity.ok(cartService.addItemToCart(userId, dto));
    }

    @PostMapping("/user/remove")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> removeItemFromUserCart(@RequestBody CartRequestDto dto) {
        Long userId = getAuthenticatedUserId();
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, dto.getProductId(), dto.getQuantity()));
    }

    @DeleteMapping("/user/clear")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> clearUserCart() {
        Long userId = getAuthenticatedUserId();
        return ResponseEntity.ok(cartService.clearCart(userId));
    }

    @PostMapping("/user/merge")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> mergeCarts(HttpServletRequest request) {
        Long userId = getAuthenticatedUserId();

        String cartToken = null;

        // 1. Leggi il cartToken dai cookie (HttpOnly)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cartToken".equals(cookie.getName())) {
                    cartToken = cookie.getValue();
                    break;
                }
            }
        }

        if (cartToken == null || cartToken.isBlank()) {
            System.out.println("[CART CONTROLLER] No cartToken found in cookies, skipping merge");
            return ResponseEntity.badRequest().body(null);
        }

        try {
            System.out.println("[CART CONTROLLER] Merging carts for userId: " + userId + ", cartToken: " + cartToken);
            return ResponseEntity.ok(cartService.mergeCarts(userId, cartToken));
        } catch (Exception e) {
            System.out.println("[CART CONTROLLER] Error merging carts: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ==== GUEST ENDPOINTS ====

    @GetMapping("/guest")
    public ResponseEntity<CartResponseDto> getGuestCart(HttpServletRequest request) {
        String cartToken = (String) request.getAttribute("cartToken");
        System.out.println("=== [GUEST CART] cartToken ricevuto: " + cartToken); // 👈 QUI

        return ResponseEntity.ok(cartService.getCart(cartToken));
    }

    @PostMapping("/guest/add")
    public ResponseEntity<CartResponseDto> addItemToGuestCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        String cartToken = (String) request.getAttribute("cartToken");
        return ResponseEntity.ok(cartService.addItemToCart(cartToken, dto));
    }

    @PostMapping("/guest/remove")
    public ResponseEntity<CartResponseDto> removeItemFromGuestCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        String cartToken = (String) request.getAttribute("cartToken");
        return ResponseEntity.ok(cartService.removeItemFromCart(cartToken, dto.getProductId(), dto.getQuantity()));
    }

    @DeleteMapping("/guest/clear")
    public ResponseEntity<CartResponseDto> clearGuestCart(HttpServletRequest request) {
        String cartToken = (String) request.getAttribute("cartToken");
        return ResponseEntity.ok(cartService.clearCart(cartToken));
    }

    // ==== SHARED ====

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserIdFromEmail(email);
    }
}

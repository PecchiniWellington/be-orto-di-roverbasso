package com.ortoroverbasso.ortorovebasso.controller.cart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ortoroverbasso.ortorovebasso.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<CartResponseDto> mergeCarts(HttpServletRequest request,
            @RequestBody(required = false) Map<String, String> payload) {
        Long userId = getAuthenticatedUserId();

        // First try to get from request attribute (set by filter)
        String cartToken = (String) request.getAttribute("cartToken");

        // If not found in attribute, try to get from request body
        if (cartToken == null && payload != null && payload.containsKey("cartToken")) {
            cartToken = payload.get("cartToken");
        }

        // If still null, try request parameter
        if (cartToken == null) {
            cartToken = request.getParameter("cartToken");
        }

        if (cartToken == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(cartService.mergeCarts(userId, cartToken));
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

    // ==== UTILS ====

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email = username
        return jwtUtil.getUserIdFromEmail(email);
    }
}

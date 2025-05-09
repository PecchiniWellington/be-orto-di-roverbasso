package com.ortoroverbasso.ortorovebasso.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<CartResponseDto> getUserCart(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(getJwtFromRequest(request));
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> addItemToUserCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        Long userId = jwtUtil.getUserIdFromToken(getJwtFromRequest(request));
        return ResponseEntity.ok(cartService.addItemToCart(userId, dto));
    }

    @PostMapping("/user/remove")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> removeItemFromUserCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        Long userId = jwtUtil.getUserIdFromToken(getJwtFromRequest(request));
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, dto.getProductId(), dto.getQuantity()));
    }

    @DeleteMapping("/user/clear")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> clearUserCart(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(getJwtFromRequest(request));
        return ResponseEntity.ok(cartService.clearCart(userId));
    }

    @PostMapping("/user/merge")
    @PreAuthorize("hasAnyRole('USER','ADMIN','CONTRIBUTOR')")
    public ResponseEntity<CartResponseDto> mergeCarts(HttpServletRequest request, @RequestParam Long userId) {
        String cartToken = getCartTokenFromRequest(request);
        Long loggedUserId = jwtUtil.getUserIdFromToken(getJwtFromRequest(request));
        return ResponseEntity.ok(cartService.mergeCarts(loggedUserId, cartToken));
    }

    // ==== GUEST ENDPOINTS ====

    @GetMapping("/guest")
    public ResponseEntity<CartResponseDto> getGuestCart(HttpServletRequest request) {
        String cartToken = getCartTokenFromRequest(request);
        return ResponseEntity.ok(cartService.getCart(cartToken));
    }

    @PostMapping("/guest/add")
    public ResponseEntity<CartResponseDto> addItemToGuestCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        String cartToken = getCartTokenFromRequest(request);
        return ResponseEntity.ok(cartService.addItemToCart(cartToken, dto));
    }

    @PostMapping("/guest/remove")
    public ResponseEntity<CartResponseDto> removeItemFromGuestCart(HttpServletRequest request,
            @RequestBody CartRequestDto dto) {
        String cartToken = getCartTokenFromRequest(request);
        return ResponseEntity.ok(cartService.removeItemFromCart(cartToken, dto.getProductId(), dto.getQuantity()));
    }

    @DeleteMapping("/guest/clear")
    public ResponseEntity<CartResponseDto> clearGuestCart(HttpServletRequest request) {
        String cartToken = getCartTokenFromRequest(request);
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

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getCartTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        String cartToken = request.getParameter("cartToken");
        if (cartToken != null) {
            return cartToken;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cartToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

package com.ortoroverbasso.ortorovebasso.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);

        if (jwt != null && jwtUtil.validateToken(jwt)) {
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            CartResponseDto cart = cartService.getCart(userId);
            return ResponseEntity.ok(cart);
        } else {
            String cartToken = getCartTokenFromRequest(request);
            CartResponseDto cart = cartService.getCart(cartToken);
            return ResponseEntity.ok(cart);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addItemToCart(
            HttpServletRequest request,
            @RequestBody CartRequestDto cartRequestDto) {
        String jwt = getJwtFromRequest(request);

        if (jwt != null && jwtUtil.validateToken(jwt)) {
            Long userId = jwtUtil.getUserIdFromToken(jwt);
            CartResponseDto cart = cartService.addItemToCart(userId, cartRequestDto);
            return ResponseEntity.ok(cart);
        } else {
            String cartToken = getCartTokenFromRequest(request);
            CartResponseDto cart = cartService.addItemToCart(cartToken, cartRequestDto);
            return ResponseEntity.ok(cart);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<CartResponseDto> removeItemFromCart(
            HttpServletRequest request,
            @RequestBody CartRequestDto cartRequestDto) {

        String jwt = getJwtFromRequest(request);

        try {
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                Long userId = jwtUtil.getUserIdFromToken(jwt);
                CartResponseDto cart = cartService.removeItemFromCart(userId, cartRequestDto.getProductId(),
                        cartRequestDto.getQuantity());
                return ResponseEntity.ok(cart);
            } else {
                String cartToken = getCartTokenFromRequest(request);
                if (cartToken == null) {
                    return ResponseEntity.status(400).body(null);
                }
                CartResponseDto cart = cartService.removeItemFromCart(cartToken, cartRequestDto.getProductId(),
                        cartRequestDto.getQuantity());
                return ResponseEntity.ok(cart);
            }
        } catch (Exception e) {
            System.out.println("Error removing item from cart: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/merge")
    public ResponseEntity<CartResponseDto> mergeCarts(
            HttpServletRequest request,
            @RequestParam Long userId) {
        String jwt = getJwtFromRequest(request);

        if (jwt != null && jwtUtil.validateToken(jwt)) {
            Long loggedUserId = jwtUtil.getUserIdFromToken(jwt);
            CartResponseDto cart = cartService.mergeCarts(loggedUserId, getCartTokenFromRequest(request));
            return ResponseEntity.ok(cart);
        } else {
            String cartToken = getCartTokenFromRequest(request);
            CartResponseDto cart = cartService.mergeCarts(userId, cartToken);
            return ResponseEntity.ok(cart);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDto> clearCart(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);

        try {
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                Long userId = jwtUtil.getUserIdFromToken(jwt);
                CartResponseDto cart = cartService.clearCart(userId);
                return ResponseEntity.ok(cart);
            } else {
                String cartToken = getCartTokenFromRequest(request);
                if (cartToken == null) {
                    return ResponseEntity.status(400).body(null);
                }
                CartResponseDto cart = cartService.clearCart(cartToken);

                return ResponseEntity.ok(cart);
            }

        } catch (Exception e) {
            System.out.println("Error clearing cart: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart() {
        String cartToken = cartService.createCart();
        return ResponseEntity.ok(cartToken);
    }

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
        // 1. Prova a leggerlo dall'Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Rimuove "Bearer "
        }

        // 2. Prova dalla query string
        String cartToken = request.getParameter("cartToken");
        if (cartToken != null) {
            return cartToken;
        }

        // 3. Prova dai cookie
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

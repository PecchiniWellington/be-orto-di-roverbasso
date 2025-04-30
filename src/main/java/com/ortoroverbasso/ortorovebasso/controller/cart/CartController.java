package com.ortoroverbasso.ortorovebasso.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private JwtUtil jwtUtil; // Utilità per decodificare il JWT

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

    @PostMapping("/merge")
    public ResponseEntity<CartResponseDto> mergeCarts(
            HttpServletRequest request,
            @RequestParam Long userId) {
        String jwt = getJwtFromRequest(request);

        if (jwt != null && jwtUtil.validateToken(jwt)) {
            // Utente autenticato, uniamo il carrello
            Long loggedUserId = jwtUtil.getUserIdFromToken(jwt); // Get logged user ID from token
            CartResponseDto cart = cartService.mergeCarts(loggedUserId, getCartTokenFromRequest(request)); // Correct
                                                                                                           // order of
                                                                                                           // params
            return ResponseEntity.ok(cart);
        } else {
            // Utente non autenticato
            String cartToken = getCartTokenFromRequest(request);
            CartResponseDto cart = cartService.mergeCarts(userId, cartToken); // Correct order of params
            return ResponseEntity.ok(cart);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCart() {
        String cartToken = cartService.createCart(); // Metodo per creare un carrello
        return ResponseEntity.ok(cartToken); // Restituisce il cartToken
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
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
        String cartToken = request.getParameter("cartToken");
        if (cartToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cartToken".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return cartToken;
    }
}

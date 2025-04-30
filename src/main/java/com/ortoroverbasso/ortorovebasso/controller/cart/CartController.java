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

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@RequestParam String cartToken) {
        CartResponseDto cart = cartService.getCart(cartToken);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addItemToCart(
            @RequestParam String cartToken,
            @RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto cart = cartService.addItemToCart(cartToken, cartRequestDto);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/merge")
    public ResponseEntity<CartResponseDto> mergeCarts(
            @RequestParam String cartToken,
            @RequestParam Long userId) {
        CartResponseDto cart = cartService.mergeCarts(cartToken, userId);
        return ResponseEntity.ok(cart);
    }
}

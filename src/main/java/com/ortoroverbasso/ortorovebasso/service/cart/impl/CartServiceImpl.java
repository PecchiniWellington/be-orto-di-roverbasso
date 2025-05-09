package com.ortoroverbasso.ortorovebasso.service.cart.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartItemDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.mapper.cart.CartMapper;
import com.ortoroverbasso.ortorovebasso.repository.cart.CartItemRepository;
import com.ortoroverbasso.ortorovebasso.repository.cart.CartRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private String getProductName(CartItemEntity item) {
        return item.getProduct().getProductInformation() != null
                ? item.getProduct().getProductInformation().getName()
                : "Unknown Product";
    }

    private CartItemDto createCartItemDto(CartItemEntity item) {
        return new CartItemDto(
                item.getProduct().getId(),
                getProductName(item),
                item.getQuantity(),
                item.getProduct().getRetailPrice());
    }

    @Override
    public String createCart() {
        CartEntity cart = new CartEntity();
        cart.setCartToken(UUID.randomUUID().toString());
        cartRepository.save(cart);
        return cart.getCartToken();
    }

    private void calculateCartTotals(List<CartItemDto> cartItems) {
        final int[] totalQuantity = { 0 };
        final double[] totalPrice = { 0.0 };

        cartItems.forEach(item -> {
            try {
                Double priceString = item.getPrice();
                double price = priceString;
                totalPrice[0] += price * item.getQuantity();
            } catch (NumberFormatException e) {
                System.out.println("Error parsing price for product " + item.getProductId() + ": " + item.getPrice());
            }

            totalQuantity[0] += item.getQuantity();
        });

        System.out.println("Total Quantity: " + totalQuantity[0]);
        System.out.println("Total Price: " + totalPrice[0]);

        cartItems.forEach(item -> {
        });
    }

    private CartResponseDto getCartInternal(CartEntity cart) {
        // Ordina gli item per data di aggiunta
        List<CartItemDto> cartItems = cart.getItems().stream()
                .sorted(Comparator.comparing(CartItemEntity::getAddedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::createCartItemDto)
                .collect(Collectors.toList());

        calculateCartTotals(cartItems);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        int totalQuantity = cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> {
                    try {
                        return item.getPrice() * item.getQuantity();
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .sum();

        cartResponseDto.setTotalQuantity(totalQuantity);
        cartResponseDto.setTotalPrice(String.format("%.2f", totalPrice));

        return cartResponseDto;
    }

    private void deleteCartItem(CartEntity cart, CartItemEntity cartItem) {
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private CartResponseDto removeItemFromCartInternal(CartEntity cart, Long productId, int quantity) {
        CartItemEntity cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity >= cartItem.getQuantity()) {
            deleteCartItem(cart, cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
            cartItemRepository.save(cartItem);
        }

        cartRepository.save(cart);

        List<CartItemDto> cartItems = cart.getItems().stream()
                .sorted(Comparator.comparing(CartItemEntity::getAddedAt)) // 👈 Ordina per ordine di aggiunta
                .map(this::createCartItemDto)
                .collect(Collectors.toList());

        calculateCartTotals(cartItems);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        int totalQuantity = cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cartResponseDto.setTotalQuantity(totalQuantity);
        cartResponseDto.setTotalPrice(String.format("%.2f", totalPrice));

        return cartResponseDto;
    }

    private CartResponseDto addItemToCartInternal(CartEntity cart, CartRequestDto cartRequestDto) {
        ProductEntity product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequestDto.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItemEntity newCartItem = new CartItemEntity();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartRequestDto.getQuantity());
            newCartItem.setAddedAt(LocalDateTime.now()); // 👈 aggiunto campo timestamp
            cart.getItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

        cartRepository.save(cart);

        List<CartItemDto> cartItems = cart.getItems().stream()
                .sorted(Comparator.comparing(
                        CartItemEntity::getAddedAt,
                        Comparator.nullsLast(Comparator.naturalOrder()) // 👈 ordina in base ad addedAt
                ))
                .map(this::createCartItemDto)
                .collect(Collectors.toList());

        calculateCartTotals(cartItems);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        int totalQuantity = cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cartResponseDto.setTotalQuantity(totalQuantity);
        cartResponseDto.setTotalPrice(String.format("%.2f", totalPrice));

        return cartResponseDto;
    }

    @Transactional
    private CartResponseDto clearCartInternal(CartEntity cart) {
        System.out.println("Before deletion, cart contains: " + cart.getItems().size() + " items.");

        List<CartItemEntity> itemsToRemove = new ArrayList<>(cart.getItems());
        cart.getItems().clear();

        if (!itemsToRemove.isEmpty()) {
            cartItemRepository.deleteAll(itemsToRemove);
            System.out.println("Items deleted from the database.");
        }

        cartRepository.save(cart);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(new ArrayList<>());

        calculateCartTotals(cartResponseDto.getItems());
        System.out.println("Cart totals calculated (should be 0).");

        return cartResponseDto;
    }

    @Override
    public CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return addItemToCartInternal(cart, cartRequestDto);
    }

    @Override
    public CartResponseDto addItemToCart(Long userId, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        return addItemToCartInternal(cart, cartRequestDto);
    }

    @Override
    public CartResponseDto removeItemFromCart(Long userId, Long productId, int quantity) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        return removeItemFromCartInternal(cart, productId, quantity);
    }

    @Override
    public CartResponseDto removeItemFromCart(String cartToken, Long productId, int quantity) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return removeItemFromCartInternal(cart, productId, quantity);
    }

    @Override
    public CartResponseDto clearCart(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        return clearCartInternal(cart);
    }

    @Override
    public CartResponseDto clearCart(String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return clearCartInternal(cart);
    }

    @Override
    public CartResponseDto mergeCarts(Long userId, String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartEntity userCart = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(new UserEntity(userId));
            return newCart;
        });

        cartRepository.save(userCart);
        return CartMapper.mapToCartResponseDto(userCart);
    }

    @Override
    public CartResponseDto getCart(String cartToken) {
        System.out.println("Getting cart with token: " + cartToken);
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return getCartInternal(cart);
    }

    @Override
    public CartResponseDto getCart(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        return getCartInternal(cart);
    }
}

// File: PickupServiceImpl.java

package com.ortoroverbasso.ortorovebasso.service.pickup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.mapper.order_custom.OrderCustomMapper;
import com.ortoroverbasso.ortorovebasso.mapper.pickup.PickupMapper;
import com.ortoroverbasso.ortorovebasso.repository.pickup.PickupRepository;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.order_custom.IOrderCustomService;

@Service
public class PickupServiceImpl implements IPickupService {

    private static final Logger logger = LoggerFactory.getLogger(PickupServiceImpl.class);

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private IOrderCustomService orderService;

    @Autowired
    private ICartService cartService;

    @Override
    public PickupResponseDto createPickup(PickupRequestDto pickupRequestDto) {
        logger.info("Creating pickup with token: {} and cartToken: {}",
                pickupRequestDto.getToken(), pickupRequestDto.getCartToken());

        if (pickupRequestDto.getToken() == null && pickupRequestDto.getCartToken() != null) {
            pickupRequestDto.setToken(pickupRequestDto.getCartToken());
        }

        PickupEntity pickupEntity = PickupMapper.toEntity(pickupRequestDto);
        PickupEntity savedEntity = pickupRepository.save(pickupEntity);
        logger.info("Initial pickup entity saved with ID: {}", savedEntity.getId());

        String cartToken = pickupRequestDto.getToken() != null
                ? pickupRequestDto.getToken()
                : pickupRequestDto.getCartToken();

        logger.info("Using cart token: {} to retrieve cart", cartToken);

        CartEntity cartEntity = cartService.getCartEntityByToken(cartToken);
        List<CartItemEntity> cartItems = cartEntity.getItems();

        if (cartItems.isEmpty() || cartItems.get(0).getCart() == null) {
            throw new RuntimeException("Cart items vuoti o senza riferimento al cart");
        }

        OrderCustomEntity orderEntity = OrderCustomMapper.fromCart(cartEntity, cartItems, savedEntity);
        OrderCustomEntity savedOrder = orderService.saveOrder(orderEntity);

        savedEntity.setOrderId(savedOrder.getId());
        savedEntity = pickupRepository.save(savedEntity);
        logger.info("Pickup entity updated with order ID: {}", savedEntity.getOrderId());

        PickupResponseDto response = PickupMapper.toDto(savedEntity);
        response.setOrderId(savedEntity.getOrderId());

        return response;
    }

    @Override
    public List<PickupResponseDto> getAllPickups() {
        List<PickupEntity> entities = pickupRepository.findAll();
        return entities.stream()
                .map(PickupMapper::toDto)
                .toList();
    }

    @Override
    public PickupResponseDto getPickupById(Long id) {
        logger.info("Getting pickup by ID: {}", id);
        PickupEntity entity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));

        PickupResponseDto response = PickupMapper.toDto(entity);
        response.setOrderId(entity.getOrderId());
        logger.info("Found pickup with ID: {} and order ID: {}", entity.getId(), entity.getOrderId());

        return response;
    }

    @Override
    public PickupResponseDto updatePickup(Long id, PickupRequestDto pickupRequestDto) {
        logger.info("Updating pickup with ID: {}", id);
        PickupEntity existingEntity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));

        if (pickupRequestDto.getToken() == null && pickupRequestDto.getCartToken() != null) {
            pickupRequestDto.setToken(pickupRequestDto.getCartToken());
            logger.info("Setting token from cartToken: {}", pickupRequestDto.getCartToken());
        }

        PickupMapper.updateEntityFromDto(pickupRequestDto, existingEntity);
        PickupEntity updatedEntity = pickupRepository.save(existingEntity);
        logger.info("Pickup updated with ID: {} and order ID: {}", updatedEntity.getId(), updatedEntity.getOrderId());

        PickupResponseDto response = PickupMapper.toDto(updatedEntity);
        response.setOrderId(updatedEntity.getOrderId());

        return response;
    }

    @Override
    public void deletePickup(Long id) {
        logger.info("Deleting pickup with ID: {}", id);
        if (!pickupRepository.existsById(id)) {
            logger.error("Pickup with ID: {} not found for deletion", id);
            throw new RuntimeException("Prenotazione con ID " + id + " non trovata");
        }
        pickupRepository.deleteById(id);
        logger.info("Pickup with ID: {} deleted successfully", id);
    }
}

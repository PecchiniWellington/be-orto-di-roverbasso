package com.ortoroverbasso.ortorovebasso.service.pickup;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartItemDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.mapper.pickup.PickupMapper;
import com.ortoroverbasso.ortorovebasso.repository.pickup.PickupRepository;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;
import com.ortoroverbasso.ortorovebasso.service.ordercustom.IOrderCustomService;

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

        CartResponseDto cartResponse = cartService.getCart(pickupRequestDto.getToken());

        OrderCustomRequestDto orderRequest = new OrderCustomRequestDto();
        List<Long> productIds = cartResponse.getItems()
                .stream()
                .map(CartItemDto::getProductId)
                .collect(Collectors.toList());

        orderRequest.setProductIds(productIds);
        orderRequest.setPickupOrder(savedEntity); // ← ora usi l'entità già salvata

        OrderCustomResponseDto orderResponse = orderService.createOrderCustom(orderRequest);

        savedEntity.setOrderId(orderResponse.getId());
        savedEntity = pickupRepository.save(savedEntity);

        PickupResponseDto response = PickupMapper.toDto(savedEntity);
        response.setOrderId(orderResponse.getId());

        return response;
    }

    @Override
    public List<PickupResponseDto> getAllPickups() {
        List<PickupEntity> entities = pickupRepository.findAll();
        return entities.stream()
                .map(PickupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PickupResponseDto getPickupById(Long id) {
        PickupEntity entity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));
        return PickupMapper.toDto(entity);
    }

    @Override
    public PickupResponseDto updatePickup(Long id, PickupRequestDto pickupRequestDto) {
        PickupEntity existingEntity = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con ID " + id + " non trovata"));

        PickupMapper.updateEntityFromDto(pickupRequestDto, existingEntity);
        PickupEntity updatedEntity = pickupRepository.save(existingEntity);

        return PickupMapper.toDto(updatedEntity);
    }

    @Override
    public void deletePickup(Long id) {
        if (!pickupRepository.existsById(id)) {
            throw new RuntimeException("Prenotazione con ID " + id + " non trovata");
        }
        pickupRepository.deleteById(id);
    }
}

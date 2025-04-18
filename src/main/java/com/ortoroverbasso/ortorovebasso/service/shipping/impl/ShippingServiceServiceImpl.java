package com.ortoroverbasso.ortorovebasso.service.shipping.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingServiceEntity;
import com.ortoroverbasso.ortorovebasso.mapper.shipping.ShippingServiceMapper;
import com.ortoroverbasso.ortorovebasso.repository.shipping.ShippingServiceRepository;
import com.ortoroverbasso.ortorovebasso.service.shipping.IShippingServiceService;

@Service
public class ShippingServiceServiceImpl implements IShippingServiceService {

    @Autowired
    private ShippingServiceRepository shippingServiceRepository;

    @Override
    public List<ShippingServiceResponseDto> getAllShippingServices() {
        List<ShippingServiceEntity> services = shippingServiceRepository.findAll();
        return services.stream()
                .map(ShippingServiceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingServiceResponseDto getShippingServiceById(Long id) {
        ShippingServiceEntity service = shippingServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShippingService not found"));
        return ShippingServiceMapper.toResponse(service);
    }

    @Override
    public ShippingServiceResponseDto createShippingService(ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceEntity service = ShippingServiceMapper.toEntity(shippingServiceRequestDto);
        service = shippingServiceRepository.save(service);
        return ShippingServiceMapper.toResponse(service);
    }

    @Override
    public ShippingServiceResponseDto updateShippingService(Long id,
            ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceEntity service = shippingServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShippingService not found"));
        return ShippingServiceMapper.toResponse(service);
    }

    @Override
    public void deleteShippingService(Long id) {
        shippingServiceRepository.deleteById(id);
    }
}

package com.ortoroverbasso.ortorovebasso.service.user.shipping_address.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;
import com.ortoroverbasso.ortorovebasso.mapper.user.shipping_address.ShippingAddressMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.shipping_address.ShippingAddressRepository;
import com.ortoroverbasso.ortorovebasso.service.user.shipping_address.IShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements IShippingAddressService {

    @Autowired
    private ShippingAddressRepository repository;

    @Override
    public ShippingAddressResponseDto createShippingAddress(ShippingAddressRequestDto dto) {
        ShippingAddressEntity entity = ShippingAddressMapper.toEntity(dto);
        ShippingAddressEntity savedEntity = repository.save(entity);
        return ShippingAddressMapper.toResponse(savedEntity);
    }

    @Override
    public ShippingAddressResponseDto updateShippingAddress(Long id, ShippingAddressRequestDto dto) {
        ShippingAddressEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCountry(dto.getCountry());
        entity.setPostcode(dto.getPostcode());
        entity.setTown(dto.getTown());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setVatNumber(dto.getVatNumber());
        entity.setCompanyName(dto.getCompanyName());
        entity.setComment(dto.getComment());
        ShippingAddressEntity updatedEntity = repository.save(entity);
        return ShippingAddressMapper.toResponse(updatedEntity);
    }

    @Override
    public List<ShippingAddressResponseDto> getAllShippingAddresses() {
        return repository.findAll().stream()
                .map(ShippingAddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingAddressResponseDto getShippingAddressById(Long id) {
        ShippingAddressEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        return ShippingAddressMapper.toResponse(entity);
    }
}

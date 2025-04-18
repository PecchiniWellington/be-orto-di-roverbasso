package com.ortoroverbasso.ortorovebasso.service.shipping.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ProductCountryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostByCountryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostResponseDto;
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

    @Override
    public List<ShippingCostResponseDto> getLowestShippingCostByCountry(
            ProductCountryRequestDto productCountryRequest) {

        ShippingCostResponseDto response = new ShippingCostResponseDto();
        response.setShippingCost(4.56);
        response.setCarrier(new CarrierResponseDto("123456", "GLS"));

        return Collections.singletonList(response);
    }

    @Override
    public List<ShippingCostByCountryResponseDto> getLowestShippingCostByCountry(String countryIsoCode) {

        List<ShippingCostByCountryResponseDto> response = new ArrayList<>();

        CarrierResponseDto carrier = new CarrierResponseDto("1234", "GLS");

        ShippingCostByCountryResponseDto shippingCost = new ShippingCostByCountryResponseDto(
                "S12435678",
                "4.76",
                carrier);

        response.add(shippingCost);

        return response;
    }
}

package com.ortoroverbasso.ortorovebasso.controller.shipping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ProductCountryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostByCountryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingCostResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCostEntity;
import com.ortoroverbasso.ortorovebasso.mapper.shipping.ShippingCostMapper;
import com.ortoroverbasso.ortorovebasso.service.shipping.IShippingServiceService;

@RestController
@RequestMapping("/api/shipping-services")
public class ShippingServiceController {

    @Autowired
    private IShippingServiceService shippingService;
    @Autowired
    private IShippingServiceService shippingServiceService;

    @PostMapping("/lowest-shipping-cost-by-country")
    public ResponseEntity<List<ShippingCostResponseDto>> getLowestShippingCostByCountry(
            @RequestBody ProductCountryRequestDto productCountryRequest) {
        List<ShippingCostResponseDto> shippingCost = shippingService
                .getLowestShippingCostByCountry(productCountryRequest);
        return ResponseEntity.ok(shippingCost);
    }

    @PostMapping
    public ResponseEntity<ShippingServiceResponseDto> createShippingService(
            @RequestBody ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceResponseDto createdShippingService = shippingService
                .createShippingService(shippingServiceRequestDto);
        return ResponseEntity.ok(createdShippingService);
    }

    @GetMapping
    public ResponseEntity<List<ShippingServiceResponseDto>> getAllShippingServices() {
        List<ShippingServiceResponseDto> shippingServices = shippingService.getAllShippingServices();
        return ResponseEntity.ok(shippingServices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingServiceResponseDto> getShippingServiceById(@PathVariable Long id) {
        ShippingServiceResponseDto shippingServiceResponseDto = shippingService.getShippingServiceById(id);
        return ResponseEntity.ok(shippingServiceResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingServiceResponseDto> updateShippingService(@PathVariable Long id,
            @RequestBody ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceResponseDto updatedShippingService = shippingService.updateShippingService(id,
                shippingServiceRequestDto);
        return ResponseEntity.ok(updatedShippingService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingService(@PathVariable Long id) {
        shippingService.deleteShippingService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lowest-shipping-costs-by-country/{countryIsoCode}")
    public List<ShippingCostByCountryResponseDto> getLowestShippingCostByCountry(@PathVariable String countryIsoCode) {
        return shippingService.getLowestShippingCostByCountry(countryIsoCode);
    }

    @PostMapping("/create-shipping-cost")
    public ShippingCostResponseDto createShippingCost(
            @RequestParam String reference,
            @RequestParam Double cost,
            @RequestParam Long carrierId) {

        ShippingCostEntity shippingCostEntity = shippingServiceService.createShippingCost(reference, cost, carrierId);

        return ShippingCostMapper.toResponse(shippingCostEntity);
    }
}

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
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.ShippingServiceResponseDto;
import com.ortoroverbasso.ortorovebasso.service.shipping.IShippingServiceService;

@RestController
@RequestMapping("/api/shipping-services")
public class ShippingServiceController {

    private final IShippingServiceService shippingService;

    @Autowired
    public ShippingServiceController(IShippingServiceService shippingService) {
        this.shippingService = shippingService;
    }

    // Crea un nuovo Shipping Service
    @PostMapping
    public ResponseEntity<ShippingServiceResponseDto> createShippingService(
            @RequestBody ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceResponseDto createdShippingService = shippingService
                .createShippingService(shippingServiceRequestDto);
        return ResponseEntity.ok(createdShippingService);
    }

    // Ottieni tutti i Shipping Services
    @GetMapping
    public ResponseEntity<List<ShippingServiceResponseDto>> getAllShippingServices() {
        List<ShippingServiceResponseDto> shippingServices = shippingService.getAllShippingServices();
        return ResponseEntity.ok(shippingServices);
    }

    // Ottieni un Shipping Service per ID
    @GetMapping("/{id}")
    public ResponseEntity<ShippingServiceResponseDto> getShippingServiceById(@PathVariable Long id) {
        ShippingServiceResponseDto shippingServiceResponseDto = shippingService.getShippingServiceById(id);
        return ResponseEntity.ok(shippingServiceResponseDto);
    }

    // Modifica un Shipping Service
    @PutMapping("/{id}")
    public ResponseEntity<ShippingServiceResponseDto> updateShippingService(@PathVariable Long id,
            @RequestBody ShippingServiceRequestDto shippingServiceRequestDto) {
        ShippingServiceResponseDto updatedShippingService = shippingService.updateShippingService(id,
                shippingServiceRequestDto);
        return ResponseEntity.ok(updatedShippingService);
    }

    // Elimina un Shipping Service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingService(@PathVariable Long id) {
        shippingService.deleteShippingService(id);
        return ResponseEntity.noContent().build();
    }
}

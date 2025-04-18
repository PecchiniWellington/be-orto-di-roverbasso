package com.ortoroverbasso.ortorovebasso.controller.user.shipping_address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.shipping_address.IShippingAddressService;

@RestController
@RequestMapping("/shipping-addresses")
public class ShippingAddressController {

    @Autowired
    private IShippingAddressService shippingAddressService;

    @PostMapping
    public ShippingAddressResponseDto createShippingAddress(@RequestBody ShippingAddressRequestDto dto) {
        return shippingAddressService.createShippingAddress(dto);
    }

    @PutMapping("/{id}")
    public ShippingAddressResponseDto updateShippingAddress(
            @PathVariable Long id,
            @RequestBody ShippingAddressRequestDto dto) {
        return shippingAddressService.updateShippingAddress(id, dto);
    }

    @GetMapping("/{id}")
    public ShippingAddressResponseDto getShippingAddressById(@PathVariable Long id) {
        return shippingAddressService.getShippingAddressById(id);
    }

    @GetMapping
    public List<ShippingAddressResponseDto> getAllShippingAddresses() {
        return shippingAddressService.getAllShippingAddresses();
    }
}

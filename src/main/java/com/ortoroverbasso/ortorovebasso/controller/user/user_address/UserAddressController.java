package com.ortoroverbasso.ortorovebasso.controller.user.user_address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.service.user.user_address.IUserAddressService;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class UserAddressController {

    @Autowired
    private IUserAddressService addressService;

    @PostMapping
    public ResponseEntity<UserAddressResponseDto> createAddress(
            @PathVariable Long userId,
            @Validated @RequestBody UserAddressRequestDto dto) {
        return ResponseEntity.ok(addressService.create(userId, dto));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressResponseDto> updateAddress(
            @PathVariable Long addressId,
            @Validated @RequestBody UserAddressRequestDto dto) {
        return ResponseEntity.ok(addressService.update(addressId, dto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.delete(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<UserAddressResponseDto> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.getById(addressId));
    }

    @GetMapping
    public ResponseEntity<List<UserAddressResponseDto>> getAllAddressesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAllByUserId(userId));
    }
}

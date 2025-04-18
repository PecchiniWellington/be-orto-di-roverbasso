package com.ortoroverbasso.ortorovebasso.service.user.shipping_address;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;

public interface IShippingAddressService {

    ShippingAddressResponseDto createShippingAddress(ShippingAddressRequestDto dto);

    ShippingAddressResponseDto updateShippingAddress(Long id, ShippingAddressRequestDto dto);

    List<ShippingAddressResponseDto> getAllShippingAddresses();

    ShippingAddressResponseDto getShippingAddressById(Long id);
}

package com.ortoroverbasso.ortorovebasso.service.user.user_address;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressResponseDto;

public interface IUserAddressService {

    UserAddressResponseDto createAddress(Long userId, UserAddressRequestDto dto);

    UserAddressResponseDto updateAddress(Long id, UserAddressRequestDto dto);

    void deleteAddress(Long id);

    UserAddressResponseDto getById(Long id);

    List<UserAddressResponseDto> getAllByUser(Long userId);
}

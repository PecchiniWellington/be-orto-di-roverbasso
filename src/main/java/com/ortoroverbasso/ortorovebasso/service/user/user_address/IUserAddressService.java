package com.ortoroverbasso.ortorovebasso.service.user.user_address;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressResponseDto;

public interface IUserAddressService {

    UserAddressResponseDto create(Long userId, UserAddressRequestDto dto);

    UserAddressResponseDto update(Long addressId, UserAddressRequestDto dto);

    void delete(Long addressId);

    UserAddressResponseDto getById(Long addressId);

    List<UserAddressResponseDto> getAllByUserId(Long userId);
}

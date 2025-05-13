package com.ortoroverbasso.ortorovebasso.service.user.user_address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.UserAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.user_address.UserAddressEntity;
import com.ortoroverbasso.ortorovebasso.exception.UserNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.user.UserAddressMapper;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.user_address.UserAddressRepository;

@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Override
    @Transactional
    public UserAddressResponseDto createAddress(Long userId, UserAddressRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserAddressEntity address = UserAddressMapper.toEntity(dto);
        address.setUser(user);

        // TODO: gestire logica per rendere un solo indirizzo primario

        UserAddressEntity saved = userAddressRepository.save(address);
        return UserAddressMapper.toResponseDto(saved);
    }

    @Override
    public List<UserAddressResponseDto> getAllByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        return userAddressRepository.findAllByUserId(userId)
                .stream()
                .map(UserAddressMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserAddressResponseDto getById(Long addressId) {
        UserAddressEntity entity = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return UserAddressMapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public UserAddressResponseDto updateAddress(Long addressId, UserAddressRequestDto dto) {
        UserAddressEntity entity = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        entity.setStreetAddress(dto.getStreetAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setPrimary(dto.isPrimary());

        UserAddressEntity updated = userAddressRepository.save(entity);
        return UserAddressMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {
        if (!userAddressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }
        userAddressRepository.deleteById(addressId);
    }
}

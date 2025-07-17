// UserAddressServiceImpl.java
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
    public UserAddressResponseDto create(Long userId, UserAddressRequestDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserAddressEntity entity = UserAddressMapper.toEntity(dto);
        entity.setUser(user);

        // Se è il primo indirizzo per l’utente, impostalo come predefinito
        if (user.getAddresses().isEmpty()) {
            entity.setPrimary(true);
        }

        return UserAddressMapper.toResponseDto(userAddressRepository.save(entity));
    }

    @Override
    @Transactional
    public UserAddressResponseDto update(Long addressId, UserAddressRequestDto dto) {
        UserAddressEntity entity = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        entity.setStreetAddress(dto.getStreetAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setPrimary(dto.isPrimary());

        return UserAddressMapper.toResponseDto(userAddressRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long addressId) {
        if (!userAddressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }
        userAddressRepository.deleteById(addressId);
    }

    @Override
    public UserAddressResponseDto getById(Long addressId) {
        UserAddressEntity entity = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return UserAddressMapper.toResponseDto(entity);
    }

    @Override
    public List<UserAddressResponseDto> getAllByUserId(Long userId) {
        return userAddressRepository.findAllByUserId(userId).stream()
                .map(UserAddressMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

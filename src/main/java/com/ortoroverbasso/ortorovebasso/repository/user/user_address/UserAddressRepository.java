package com.ortoroverbasso.ortorovebasso.repository.user.user_address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.user.user_address.UserAddressEntity;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {
    boolean existsByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    boolean existsByUserId(Long userId);

    List<UserAddressEntity> findAllByUserId(Long id);
}

package com.ortoroverbasso.ortorovebasso.repository.user.user_security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.user.user_security.UserSecurityEntity;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurityEntity, Long> {
    Optional<UserSecurityEntity> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}

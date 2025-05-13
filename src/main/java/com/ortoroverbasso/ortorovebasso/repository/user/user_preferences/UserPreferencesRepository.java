package com.ortoroverbasso.ortorovebasso.repository.user.user_preferences;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.user.user_preferences.UserPreferencesEntity;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Long> {
    Optional<UserPreferencesEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}

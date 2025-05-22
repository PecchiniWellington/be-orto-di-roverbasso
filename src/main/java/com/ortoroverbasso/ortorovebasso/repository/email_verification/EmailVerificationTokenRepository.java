package com.ortoroverbasso.ortorovebasso.repository.email_verification;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.email_verification.EmailVerificationTokenEntity;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationTokenEntity, Long> {
    Optional<EmailVerificationTokenEntity> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUser_Id(Long userId);

}

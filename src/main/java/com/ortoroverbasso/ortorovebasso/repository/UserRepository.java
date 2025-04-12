package com.ortoroverbasso.ortorovebasso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}


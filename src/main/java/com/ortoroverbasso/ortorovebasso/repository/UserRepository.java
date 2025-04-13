package com.ortoroverbasso.ortorovebasso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);
}

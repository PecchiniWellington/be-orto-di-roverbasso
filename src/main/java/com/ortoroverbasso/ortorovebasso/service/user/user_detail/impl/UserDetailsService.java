package com.ortoroverbasso.ortorovebasso.service.user.user_detail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.entity.user.UserDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;
import com.ortoroverbasso.ortorovebasso.service.user.user_detail.IUserDetailService;

@Service
public class UserDetailsService implements IUserDetailService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerca l'utente dal repository
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Crea e restituisci un oggetto UserDetailEntity
        return new UserDetailEntity(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAuthorities());
    }
}

package com.ortoroverbasso.ortorovebasso.service.user.user_detail;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetailService {
    public UserDetails loadUserByUsername(String username);
}

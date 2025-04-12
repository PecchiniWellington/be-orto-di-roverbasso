package com.ortoroverbasso.ortorovebasso.service.user;

import com.ortoroverbasso.ortorovebasso.model.User;

public interface IUserService {
    User createUser(User user);

    User getAllUsers();

    User getUserById(Long id);
}

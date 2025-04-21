package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.entity.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    Optional<User> findByEmail(String email);
}

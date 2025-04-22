package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.entity.User;

import java.util.Optional;

public interface UserService {
    void registerUser(RegisterRequest request);

    User findByEmail(String email);
}

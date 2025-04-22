package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.dto.UserResponse;
import com.gopiwebdev.ecommerce.user_service.entity.User;


public interface UserService {
    void registerUser(RegisterRequest request);

    UserResponse getCurrentUser();
}

package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.entity.User;
import com.gopiwebdev.ecommerce.user_service.exception.UsernameAlreadyExistsException;
import com.gopiwebdev.ecommerce.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User registerUser(RegisterRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepo.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}

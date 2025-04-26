package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.dto.UserResponse;
import com.gopiwebdev.ecommerce.user_service.entity.User;
import com.gopiwebdev.ecommerce.user_service.exception.UsernameAlreadyExistsException;
import com.gopiwebdev.ecommerce.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserServiceImplementation userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "password123");

        when(repository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(request));

        verify(repository, times(1)).existsByUsername(request.getUsername());
        verify(repository, times(0)).save(any(User.class)); // save should not be called
    }
}
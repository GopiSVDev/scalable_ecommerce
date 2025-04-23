package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.entity.User;
import com.gopiwebdev.ecommerce.user_service.exception.UsernameAlreadyExistsException;
import com.gopiwebdev.ecommerce.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    void registerUser_ShouldSaveUser_WhenUsernameIsNew() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setEmail("new@example.com");

        // Mocking repo and encoder behavior
        when(repository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.registerUser(request);

        verify(repository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("password");
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");

        when(repository.existsByUsername("existinguser")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.registerUser(request);
        });

        verify(repository, never()).save(any());
    }
}
package com.gopiwebdev.ecommerce.user_service.service;

import com.gopiwebdev.ecommerce.user_service.dto.RegisterRequest;
import com.gopiwebdev.ecommerce.user_service.entity.User;
import com.gopiwebdev.ecommerce.user_service.exception.UsernameAlreadyExistsException;
import com.gopiwebdev.ecommerce.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    void testRegisterUser_UsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest("testuser@gmail.com", "testuser", "testpassword");

        when(repository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.registerUser(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest("testuser@gmail.com", "testuser", "testpassword");

        when(repository.existsByUsername(request.getUsername())).thenReturn(false);

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        userService.registerUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("testuser@gmail.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());

        verify(passwordEncoder, times(1)).encode(request.getPassword());
    }
}
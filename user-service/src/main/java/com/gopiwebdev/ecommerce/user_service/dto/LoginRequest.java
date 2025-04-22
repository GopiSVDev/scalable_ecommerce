package com.gopiwebdev.ecommerce.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

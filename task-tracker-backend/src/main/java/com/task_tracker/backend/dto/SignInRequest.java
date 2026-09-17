package com.task_tracker.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email contains invalid characters")
        String email,
        @NotBlank(message = "Password is required")
        String password) {
}

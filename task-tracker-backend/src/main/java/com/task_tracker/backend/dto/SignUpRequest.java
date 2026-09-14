package com.task_tracker.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank
        @Size(min = 5, message = "Email must contain from 5 characters")
        @Email(message = "Email contains invalid characters")
        String email,
        @NotBlank
        @Size(min = 5, max = 20, message = "Password must contain from 5 to 20 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>\\[\\]\\/`~+=\\-_';]+$",
                message = "Password contains invalid characters"
        )
        String password) {
    public SignUpRequest {
        if (email != null) {
            email = email.trim();
        }
    }
}
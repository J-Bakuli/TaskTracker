package com.task_tracker.backend.dto;

public record SignInResponse(
        String token,
        long expiresIn) {
}

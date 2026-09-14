package com.task_tracker.backend.dto;

public record ApiErrorResponse(
        int status,
        String error,
        String message) {
}

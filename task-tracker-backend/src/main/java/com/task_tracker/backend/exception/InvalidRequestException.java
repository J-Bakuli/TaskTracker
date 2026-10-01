package com.task_tracker.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(HttpStatus badRequest, String message) {
        super(message);
    }
}

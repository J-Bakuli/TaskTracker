package com.task_tracker.backend.controller;

import com.task_tracker.backend.dto.SignUpRequest;
import com.task_tracker.backend.dto.UserResponse;
import com.task_tracker.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    public UserResponse register(
            @Valid @RequestBody SignUpRequest request
    ) {
        return authService.register(request);
    }
}

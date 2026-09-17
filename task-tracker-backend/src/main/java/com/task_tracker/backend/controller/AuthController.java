package com.task_tracker.backend.controller;

import com.task_tracker.backend.dto.SignInRequest;
import com.task_tracker.backend.dto.SignInResponse;
import com.task_tracker.backend.dto.SignUpRequest;
import com.task_tracker.backend.dto.SignUpResponse;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.service.AuthService;
import com.task_tracker.backend.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    public UserResponse register(
            @Valid @RequestBody SignUpRequest request
    ) {
        return authService.register(request);
    public SignUpResponse register(@Valid @RequestBody SignUpRequest request, HttpServletResponse response) {
        UserEntity userEntity = authService.register(request);
        String jwtToken = jwtService.generateToken(userEntity);
        response.setHeader("Authorization", "Bearer " + jwtToken);
        return new SignUpResponse(userEntity.getId(), userEntity.getEmail());
    }
    }
}

package com.task_tracker.backend.controller;

import com.task_tracker.backend.dto.UserResponse;
import com.task_tracker.backend.model.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    public UserResponse getUser(@AuthenticationPrincipal UserEntity userEntity) {
        return new UserResponse(userEntity.getId(), userEntity.getEmail());
    }
}
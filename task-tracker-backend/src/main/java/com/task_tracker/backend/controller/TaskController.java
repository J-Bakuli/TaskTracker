package com.task_tracker.backend.controller;

import com.task_tracker.backend.dto.TaskResponse;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tasks")
    public List<TaskResponse> getTasks(@AuthenticationPrincipal UserEntity userEntity) {
        return taskService.getTasksByUserId(userEntity.getId());
    }
}

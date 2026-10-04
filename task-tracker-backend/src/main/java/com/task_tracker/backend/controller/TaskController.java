package com.task_tracker.backend.controller;

import com.task_tracker.backend.dto.TaskCreateRequest;
import com.task_tracker.backend.dto.TaskResponse;
import com.task_tracker.backend.dto.TaskUpdateRequest;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tasks")
    public TaskResponse createTask(@AuthenticationPrincipal UserEntity userEntity,
                                   @Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        return taskService.createTask(userEntity.getId(), taskCreateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/tasks/{id}")
    public TaskResponse updateTask(@PathVariable("id") Long taskId,
                                   @AuthenticationPrincipal UserEntity userEntity,
                                   @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.updateTask(userEntity.getId(), taskId, taskUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id") Long taskId,
                           @AuthenticationPrincipal UserEntity userEntity) {
        taskService.deleteTask(userEntity.getId(), taskId);
    }
}

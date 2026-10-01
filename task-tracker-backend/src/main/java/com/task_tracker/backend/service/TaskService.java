package com.task_tracker.backend.service;

import com.task_tracker.backend.dto.TaskCreateRequest;
import com.task_tracker.backend.dto.TaskResponse;
import com.task_tracker.backend.dto.TaskUpdateRequest;
import com.task_tracker.backend.exception.ResourceNotFoundException;
import com.task_tracker.backend.model.TaskEntity;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.repository.TaskRepository;
import com.task_tracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        List<TaskEntity> taskEntities = taskRepository.findByUser_Id(userId);
        return taskEntities.stream()
                .map(TaskResponse::from)
                .toList();
    }

    public TaskResponse createTask(Long userId, TaskCreateRequest request) {
        log.debug("Create task for userId={}, request={}", userId, request);
        UserEntity user = userRepository.getReferenceById(userId);
        TaskEntity taskEntity = TaskEntity.createNewTaskEntity(request, user);
        TaskEntity savedEntity = taskRepository.save(taskEntity);
        log.info("Created task for userId={}, request={}", userId, request);
        return TaskResponse.from(savedEntity);
    }

    @Transactional
    public TaskResponse updateTask(Long userId, Long taskId, TaskUpdateRequest request) {
        log.debug("Update task for userId={}, request={}", userId, request);
        TaskEntity task = taskRepository.findByIdAndUser_Id(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task is not found"));
        TaskEntity taskEntity = TaskEntity
                .updateTaskEntity(task, request.title(), request.description(), request.status());
        TaskEntity savedEntity = taskRepository.save(taskEntity);
        log.info("Updated task for userId={}, request={}", userId, request);
        return TaskResponse.from(savedEntity);
    }
}

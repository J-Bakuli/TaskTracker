package com.task_tracker.backend.service;

import com.task_tracker.backend.dto.TaskResponse;
import com.task_tracker.backend.model.TaskEntity;
import com.task_tracker.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        List<TaskEntity> taskEntities = taskRepository.findByUser_Id(userId);
        return taskEntities.stream()
                .map(TaskResponse::from)
                .toList();
    }
}

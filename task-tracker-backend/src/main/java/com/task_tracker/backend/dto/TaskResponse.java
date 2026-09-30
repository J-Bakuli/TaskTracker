package com.task_tracker.backend.dto;

import com.task_tracker.backend.model.Status;
import com.task_tracker.backend.model.TaskEntity;

import java.time.LocalDateTime;

public record TaskResponse (
        Long id,
        String title,
        String description,
        Status status,
        LocalDateTime doneAt) {

    public static TaskResponse from(TaskEntity taskEntity) {
        return new TaskResponse(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getDoneAt()
        );
    }
}

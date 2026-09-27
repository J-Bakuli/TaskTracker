package com.task_tracker.backend.dto;

import com.task_tracker.backend.model.Status;

import java.time.LocalDateTime;

public record TaskResponse (
        Long id,
        String title,
        String description,
        Status status,
        LocalDateTime doneAt) {
}

package com.task_tracker.backend.dto;

import com.task_tracker.backend.model.Status;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest(
        @Size(min = 1, max = 255, message = "Title must be less than 255 characters")
        String title,
        @Size(min = 1, max = 4000, message = "Description must be less than 4000 characters")
        String description,
        Status status
) {
}

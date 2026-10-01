package com.task_tracker.backend.model;

import com.task_tracker.backend.dto.TaskCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private LocalDateTime doneAt;

    public static TaskEntity createNewTaskEntity(TaskCreateRequest request, UserEntity user) {
        TaskEntity task = new TaskEntity();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(Status.PENDING);
        task.setUser(user);
        task.setDoneAt(null);
        return task;
    }

    public static TaskEntity updateTaskEntity(TaskEntity task, String newTitle, String newDescription, Status newStatus) {
        if (newTitle != null) {
            task.setTitle(newTitle);
        }
        if (newDescription != null) {
            task.setDescription(newDescription);
        }
        if (newStatus != null) {
            task.setStatus(newStatus);
        }
        if (task.getStatus() == Status.DONE) {
            if (task.getDoneAt() == null) {
                task.setDoneAt(LocalDateTime.now());
            }
        }
        if (task.getStatus() == Status.PENDING) {
            task.setDoneAt(null);
        }
        return task;
    }
}

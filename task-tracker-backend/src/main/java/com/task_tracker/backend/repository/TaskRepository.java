package com.task_tracker.backend.repository;

import com.task_tracker.backend.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUser_Id(Long userId);
}

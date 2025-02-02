package com.managermate.backend.repository;

import com.managermate.backend.model.Task;
import com.managermate.backend.model.User;
import com.managermate.backend.util.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByAssignedTo(User assignedTo);
    Optional<Task> findByTaskId(Integer taskId);
    List<Task> findByCreatedBy(User createdBy);
    List<Task> findByStatus(TaskStatus status);
}

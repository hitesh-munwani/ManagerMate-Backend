package com.managermate.backend.service;

import com.managermate.backend.exception.TaskNotFoundException;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Task;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.TaskRepository;
import com.managermate.backend.repository.UserRepository;
import com.managermate.backend.util.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getTasksAssignedToUser(Integer userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return taskRepository.findByAssignedTo(user.get());
        } else{
            throw new UserNotFoundException(String.format("User with id %d does not exist", userId));
        }
    }

    public Optional<Task> getTaskById(Integer taskId) {
        return taskRepository.findByTaskId(taskId);
    }

    public Task updateTaskStatus(Integer taskId, TaskStatus status) throws TaskNotFoundException {
        Optional<Task> taskOptional = taskRepository.findByTaskId(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(status);
            return taskRepository.save(task);
        }
        throw new TaskNotFoundException("Task not found with this id");
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}

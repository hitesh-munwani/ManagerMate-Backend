package com.managermate.backend.service;

import com.managermate.backend.dto.TaskDTO;
import com.managermate.backend.exception.TaskNotFoundException;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Task;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.TaskRepository;
import com.managermate.backend.repository.UserRepository;
import com.managermate.backend.util.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(TaskDTO taskDTO) throws UserNotFoundException {
        User manager = userRepository.findById(taskDTO.getManagerId())
                .orElseThrow(() -> new UserNotFoundException("Manager not found"));

        Task task = new Task();
        task.setTaskName(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(TaskStatus.PENDING);
        task.setAssignedBy(manager);
        task.setCreatedBy(manager);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public void deleteTask(Integer taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

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

            if(status.equals(TaskStatus.PENDING)){
                task.setStartedAt(LocalDateTime.now());
            }

            return taskRepository.save(task);
        }
        throw new TaskNotFoundException("Task not found with this id");
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task assignTaskToUser(TaskDTO taskDTO) throws TaskNotFoundException, UserNotFoundException {
        Task task = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User manager = userRepository.findById(taskDTO.getManagerId())
                .orElseThrow(() -> new UserNotFoundException("Manager not found with this id"));

        task.setAssignedTo(user);
        task.setAssignedBy(manager);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(taskDTO.getDueDate());

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(Integer managerId) throws UserNotFoundException {

        User manager = userRepository.findByUserId(managerId);

        return taskRepository.findByCreatedBy(manager);
    }
}

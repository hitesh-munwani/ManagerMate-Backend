package com.managermate.backend.controller;

import com.managermate.backend.dto.TaskDTO;
import com.managermate.backend.exception.TaskNotFoundException;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Task;
import com.managermate.backend.service.TaskService;
import com.managermate.backend.util.enums.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid task data")
    })
    @PostMapping("/create")
    public Task createTask(
            @Parameter(description = "DTO containing task details for creation") @RequestBody TaskDTO taskDTO) throws UserNotFoundException {
        return taskService.createTask(taskDTO);
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{taskId}")
    public String deleteTask(
            @Parameter(description = "ID of the task to delete") @PathVariable Integer taskId) throws TaskNotFoundException {
        taskService.deleteTask(taskId);
        return "Task deleted successfully";
    }

    @Operation(summary = "Get all tasks assigned to a user", description = "Returns a list of tasks assigned to a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tasks"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/assigned-to/{userId}")
    public List<Task> getTasksAssignedToUser(
            @Parameter(description = "ID of the user to retrieve tasks for") @PathVariable Integer userId) throws UserNotFoundException {
        return taskService.getTasksAssignedToUser(userId);
    }

    @Operation(summary = "Get a task by ID", description = "Returns a task based on the provided task ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task details"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{taskId}")
    public Optional<Task> getTaskById(
            @Parameter(description = "ID of the task to retrieve") @PathVariable Integer taskId) {
        return taskService.getTaskById(taskId);
    }

    @Operation(summary = "Update task status", description = "Updates the status of a specific task by task ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated task"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{taskId}/status")
    public Task updateTaskStatus(
            @Parameter(description = "ID of the task to update") @PathVariable Integer taskId,
            @Parameter(description = "New status of the task") @RequestBody TaskStatus status) throws TaskNotFoundException {
        return taskService.updateTaskStatus(taskId, status);
    }

    @Operation(summary = "Get tasks by status", description = "Returns a list of tasks based on the specified status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tasks with specified status")
    })
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(
            @Parameter(description = "Status to filter tasks by") @PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @Operation(summary = "Assign a task to an employee", description = "Assigns a specific task to an employee by task ID and user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully assigned"),
            @ApiResponse(responseCode = "404", description = "Task or User not found")
    })
    @PutMapping("/assign")
    public Task assignTaskToEmployee(
            @Parameter(description = "DTO containing task and user ID for assignment") @RequestBody TaskDTO taskDTO) throws TaskNotFoundException, UserNotFoundException {
        return taskService.assignTaskToUser(taskDTO);
    }

}

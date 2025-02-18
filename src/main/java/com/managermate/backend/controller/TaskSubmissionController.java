package com.managermate.backend.controller;

import com.managermate.backend.exception.TaskNotFoundException;
import com.managermate.backend.model.TaskSubmission;
import com.managermate.backend.service.TaskSubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-submissions")
public class TaskSubmissionController {

    private final TaskSubmissionService taskSubmissionService;

    @Operation(summary = "Get all task submissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of task submissions retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskSubmission.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<TaskSubmission>> getAllTaskSubmissions() {
        List<TaskSubmission> submissions = taskSubmissionService.getAllTaskSubmissions();
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "Get task submissions by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of task submissions by user retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskSubmission.class))),
            @ApiResponse(responseCode = "404", description = "No task submissions found for the user",
                    content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskSubmission>> getTaskSubmissionsByUser(@PathVariable Integer userId) {
        List<TaskSubmission> submissions = taskSubmissionService.getSubmissionsByUserId(userId);
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "Get task submissions for a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of task submissions for the task retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskSubmission.class))),
            @ApiResponse(responseCode = "404", description = "No task submissions found for the task",
                    content = @Content)
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskSubmission>> getTaskSubmissionsByTask(@PathVariable Integer taskId) {
        List<TaskSubmission> submissions = taskSubmissionService.getSubmissionsByTaskId(taskId);
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "Submit a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task submitted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)
    })
    @PostMapping("/submit")
    public ResponseEntity<String> submitTask(
            @RequestParam Integer taskId,
            @RequestParam Integer userId,
            @RequestParam String comment,
            @RequestParam(required = false) MultipartFile file) throws IOException, TaskNotFoundException {

        taskSubmissionService.submitTask(taskId, userId, comment, file);

        return ResponseEntity.ok("Task submitted successfully");
    }
}

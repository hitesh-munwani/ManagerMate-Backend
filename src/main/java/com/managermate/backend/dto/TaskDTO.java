package com.managermate.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private Integer userId;
    private Integer managerId;
    private LocalDateTime dueDate;
}

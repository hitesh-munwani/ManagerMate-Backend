package com.managermate.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private Integer userId;
    private Integer managerId;
    private LocalDateTime dueDate;
}

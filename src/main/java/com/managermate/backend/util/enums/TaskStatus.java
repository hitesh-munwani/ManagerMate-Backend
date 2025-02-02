package com.managermate.backend.util.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The status of a task")
public enum TaskStatus {

    @Schema(description = "The task is pending and has not started yet")
    PENDING,

    @Schema(description = "The task is currently being worked on")
    IN_PROGRESS,

    @Schema(description = "The task is completed")
    COMPLETED
}

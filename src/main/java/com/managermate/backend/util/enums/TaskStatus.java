package com.managermate.backend.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Schema(description = "The status of a task")
@AllArgsConstructor
@Getter
public enum TaskStatus {

    @Schema(description = "The task is pending and has not started yet")
    PENDING("Pending"),

    @Schema(description = "The task is currently being worked on")
    IN_PROGRESS("In_Progress"),

    @Schema(description = "The task is completed")
    COMPLETED("Completed");

    private final String key;

    @JsonValue
    public String getKey() {
        return key;
    }

    @JsonCreator
    public static TaskStatus fromString(String value) {
        return Arrays.stream(TaskStatus.values())
                .filter(status -> status.key.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid TaskStatus: " + value));
    }
}

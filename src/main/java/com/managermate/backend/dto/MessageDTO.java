package com.managermate.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageDTO {
    @NotBlank(message = "Message content must not be blank")
    private String message;

    @NotNull(message = "Receiver ID must not be null")
    @Min(value = 1, message = "Receiver ID must be a positive integer")
    private Integer receiverId;

    @NotNull(message = "Sender ID (current user ID) must not be null")
    @Min(value = 1, message = "Sender ID must be a positive integer")
    private Integer senderId;
}

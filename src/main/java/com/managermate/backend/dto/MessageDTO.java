package com.managermate.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageDTO {

    @Schema(description = "The content of the message")
    @NotBlank(message = "Message content must not be blank")
    private String message;

    @Schema(description = "Sender's user ID")
    @NotNull(message = "Receiver ID must not be null")
    @Min(value = 1, message = "Receiver ID must be a positive integer")
    private Integer receiverId;

    @Schema(description = "Receiver's user ID")
    @NotNull(message = "Sender ID (current user ID) must not be null")
    @Min(value = 1, message = "Sender ID must be a positive integer")
    private Integer senderId;
}

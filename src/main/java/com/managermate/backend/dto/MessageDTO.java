package com.managermate.backend.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String message;
    private Long receiverId;
    private Long senderId;
    //NOTE: senderId is currentUserId
}

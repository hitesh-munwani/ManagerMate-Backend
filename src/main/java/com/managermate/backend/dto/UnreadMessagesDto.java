package com.managermate.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UnreadMessagesDto {
    private Integer userId;
    private String userName;
    private Integer unreadCount;
    private String lastMessage;

    public UnreadMessagesDto(Integer userId, String userName, Integer unreadCount, String lastMessage) {
        this.userId = userId;
        this.userName = userName;
        this.unreadCount = unreadCount;
        this.lastMessage = lastMessage;
    }
}


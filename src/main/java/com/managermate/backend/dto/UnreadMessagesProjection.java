package com.managermate.backend.dto;

public interface UnreadMessagesProjection {
    Integer getUserId();
    String getUserName();
    Integer getUnreadCount();
    String getLastMessage();
}

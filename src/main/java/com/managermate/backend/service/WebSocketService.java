package com.managermate.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(Integer userId, String messageContent) {
        messagingTemplate.convertAndSendToUser(String.valueOf(userId), "/queue/messages", messageContent);
    }

    public void broadcastMessage(String messageContent) {
        messagingTemplate.convertAndSend("/topic/messages", messageContent);
    }
}

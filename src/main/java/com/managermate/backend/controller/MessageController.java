package com.managermate.backend.controller;

import com.managermate.backend.dto.MessageDTO;
import com.managermate.backend.model.Message;
import com.managermate.backend.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public String sendMessage(@Valid @RequestBody MessageDTO messageDTO){
        return messageService.sendMessage(messageDTO);
    }

    @GetMapping("/conversation/{userID}/{otherUserID}")
    public List<Message> getConversation(
        @PathVariable Integer userID,
        @PathVariable Integer otherUserID
    ){
        return messageService.getConversation(userID, otherUserID);
    }

    @PutMapping("/mark-as-seen/{messageId}")
    public String markAsSeen(@PathVariable Long messageId) {
        return messageService.markAsSeen(messageId);
    }
}

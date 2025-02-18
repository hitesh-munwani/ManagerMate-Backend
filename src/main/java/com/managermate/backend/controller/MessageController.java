package com.managermate.backend.controller;

import com.managermate.backend.dto.MessageDTO;
import com.managermate.backend.dto.UnreadMessagesDto;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Message;
import com.managermate.backend.model.User;
import com.managermate.backend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Tag(name = "Message API", description = "Manage messages and conversations")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send a new message")
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@Valid @RequestBody MessageDTO messageDTO) {
        String result = messageService.sendMessage(messageDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get conversation between two users")
    @GetMapping("/conversation/{userID}/{otherUserID}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable Integer userID,
            @PathVariable Integer otherUserID
    ) {
        List<Message> messages = messageService.getConversation(userID, otherUserID);
        return ResponseEntity.ok(messages);
    }


    @Operation(summary = "Get unread messages manager employees",
            description = "Retrieve a list of employees names with their unread messages count and last message.")
    @GetMapping("/unread/{managerId}")
    public List<UnreadMessagesDto> getUnreadMessages(@PathVariable Integer managerId) {
        return messageService.getUnreadMessagesForManager(managerId);
    }
    @Operation(summary = "Mark a message as seen")
    @PutMapping("/mark-as-seen/{messageId}")
    public ResponseEntity<String> markAsSeen(@PathVariable Long messageId) {
        String result = messageService.markAsSeen(messageId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Handle WebSocket messages")
    @MessageMapping("/chat.send")
    public void handleWebSocketMessage(@Valid MessageDTO messageDTO) {
        messageService.sendMessage(messageDTO);
    }
}

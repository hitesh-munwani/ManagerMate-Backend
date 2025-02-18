package com.managermate.backend.service;

import com.managermate.backend.dto.MessageDTO;
import com.managermate.backend.dto.UnreadMessagesDto;
import com.managermate.backend.dto.UnreadMessagesProjection;
import com.managermate.backend.model.Message;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.MessageRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public String sendMessage(MessageDTO messageDTO) {

        Message message = Message.builder()
                .content(messageDTO.getMessage())
                .receiver(userRepository.findById(messageDTO.getReceiverId()).orElse(null))
                .sender(userRepository.findById(messageDTO.getSenderId()).orElse(null))
                .sentAt(LocalDateTime.now())
                .build();
        message.setContent(messageDTO.getMessage());

        messageRepository.save(message);
        return "Message sent successfully";
    }


    public List<Message> getConversation(Integer userID, Integer otherUserID) {
        Set<Message> messageSet = new TreeSet<>(Comparator.comparing(Message::getId));

        messageSet.addAll(messageRepository.findBySenderIdAndReceiverId(userID, otherUserID));
        messageSet.addAll(messageRepository.findBySenderIdAndReceiverId(otherUserID, userID));

        return new ArrayList<>(messageSet)
                .stream()
                .sorted(Comparator.comparing(Message::getSentAt))
                .toList();
    }

    public String markAsSeen(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (message.getReadAt() != null) {
            return "Success.";
        }

        // Mark the selected message as read
        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);

        // Mark all previous unread messages as read
        List<Message> unreadMessages = messageRepository.findUnreadMessagesBefore(messageId, message.getSenderId(), message.getReceiverId());

        for (Message unreadMessage : unreadMessages) {
            unreadMessage.setReadAt(LocalDateTime.now());
        }

        messageRepository.saveAll(unreadMessages);

        return "Success.";
    }

    public List<UnreadMessagesDto> getUnreadMessagesForManager(Integer managerId) {
        List<UnreadMessagesProjection> unreadMessages = messageRepository.findUnreadMessages(managerId);

        // Convert projections to DTOs
        return unreadMessages.stream()
                .map(msg -> new UnreadMessagesDto(
                        msg.getUserId(),
                        msg.getUserName(),
                        msg.getUnreadCount(),
                        msg.getLastMessage()
                ))
                .collect(Collectors.toList());
    }

}

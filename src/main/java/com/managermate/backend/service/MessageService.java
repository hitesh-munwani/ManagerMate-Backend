package com.managermate.backend.service;

import com.managermate.backend.dto.MessageDTO;
import com.managermate.backend.model.Message;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.MessageRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public String sendMessage(MessageDTO messageDTO) {

        Message message = Message.builder()
                .content(messageDTO.getMessage())
                .receiverId(userRepository.findById(messageDTO.getReceiverId()).orElse(null))
                .senderId(userRepository.findById(messageDTO.getSenderId()).orElse(null))
                .sentAt(LocalDateTime.now())
                .build();
        message.setContent(messageDTO.getMessage());

        messageRepository.save(message);
        return "Message sent successfully";
    }


    public List<Message> getConversation(Integer userID, Integer otherUserID) {
        Set<Message> messageSet = new TreeSet<>(Comparator.comparing(Message::getId));

        User user = userRepository.findById(userID).orElse(null);
        User otherUser = userRepository.findById(otherUserID).orElse(null);

        messageSet.addAll(messageRepository.findBySenderIdAndReceiverId(user, otherUser));
        messageSet.addAll(messageRepository.findBySenderIdAndReceiverId(otherUser, user));

        return new ArrayList<>(messageSet)
                .stream()
                .sorted(Comparator.comparing(Message::getSentAt))
                .toList();
    }

    public String markAsSeen(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (message.getReadAt()!=null) {
            return "Success.";
        }

        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);

        return "Success.";
    }
}

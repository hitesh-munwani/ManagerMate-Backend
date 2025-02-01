package com.managermate.backend.repository;

import com.managermate.backend.model.Message;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(User senderId, User receiverId);
}

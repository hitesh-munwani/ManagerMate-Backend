package com.managermate.backend.repository;

import com.managermate.backend.dto.UnreadMessagesDto;
import com.managermate.backend.dto.UnreadMessagesProjection;
import com.managermate.backend.model.Message;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.sender.userId = :senderId AND m.receiver.userId = :receiverId")
    List<Message> findBySenderIdAndReceiverId(
            @Param("senderId") Integer senderId,
            @Param("receiverId") Integer receiverId
    );

    @Query("SELECT m FROM Message m WHERE m.id < :messageId AND m.receiver.userId = :receiverId AND m.sender.userId = :senderId AND m.readAt IS NULL ORDER BY m.id DESC")
    List<Message> findUnreadMessagesBefore(
            @Param("messageId") Long messageId,
            @Param("senderId") Integer senderId,
            @Param("receiverId") Integer receiverId
    );

    @Query("""
    SELECT e.userId AS userId,
           e.user_name AS userName, 
           COUNT(m.id) AS totalMessages, 
           COALESCE(SUM(CASE WHEN m.readAt IS NULL AND m.receiver.userId = :managerId THEN 1 ELSE 0 END), 0) AS unreadCount,\s
           (SELECT m1.content 
            FROM Message m1 
            WHERE (m1.sender.userId = e.userId OR m1.receiver.userId = e.userId)
            ORDER BY m1.id DESC LIMIT 1) AS lastMessage
    FROM User e
    JOIN Message m ON e.userId = m.receiver.userId OR e.userId = m.sender.userId
    WHERE e.userId <> :managerId
    GROUP BY e.userId, e.user_name
""")
    List<UnreadMessagesProjection> findUnreadMessages(@Param("managerId") Integer managerId);

}

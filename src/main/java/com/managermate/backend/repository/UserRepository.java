package com.managermate.backend.repository;

import com.managermate.backend.model.Role;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email, considering isActive = true or null
    @Query("SELECT u FROM User u WHERE u.email = :email AND (u.isActive = TRUE OR u.isActive IS NULL)")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email AND (u.isActive = TRUE OR u.isActive IS NULL)")
    boolean existsByEmail(@Param("email") String email);

    // Find users by role and isActive = true or null
    List<User> findByRoleAndIsActiveTrueOrIsActiveNull(Role role);

    @Query("SELECT u FROM User u WHERE u.manager = :manager AND (u.isActive = TRUE OR u.isActive IS NULL)")
    List<User> findActiveOrUnspecifiedUsersByManager(@Param("manager") User manager);

    // Find manager by employee ID, considering isActive = true or null
    Optional<User> findByUserIdAndIsActiveTrueOrIsActiveNull(Integer userId);

    User findByUserId(Integer userId);
}

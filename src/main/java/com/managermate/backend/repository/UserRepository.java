package com.managermate.backend.repository;

import com.managermate.backend.model.Role;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email, considering isActive = true or null
    Optional<User> findByEmailAndIsActiveTrueOrIsActiveNull(String email);

    // Check if email exists and is active or null
    boolean existsByEmailAndIsActiveTrueOrIsActiveNull(String email);

    // Find users by role and isActive = true or null
    List<User> findByRoleAndIsActiveTrueOrIsActiveNull(Role role);

    // Find employees by manager, only active or null users
    List<User> findByManagerAndIsActiveTrueOrIsActiveNull(User manager);

    // Find manager by employee ID, considering isActive = true or null
    Optional<User> findByUserIdAndIsActiveTrueOrIsActiveNull(Integer userId);
}

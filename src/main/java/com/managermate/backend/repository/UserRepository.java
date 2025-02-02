package com.managermate.backend.repository;

import com.managermate.backend.model.Role;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);

    //Find employees using manager
    List<User> findByManager(User manager);

    // Find manager by employee ID
    User findByUserId(Integer userId);
}

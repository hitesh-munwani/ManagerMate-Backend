package com.managermate.backend.repository;

import com.managermate.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
   Role findByRoleNameIgnoreCase(String roleName);

    boolean existsByRoleName(String roleName);
}

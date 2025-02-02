package com.managermate.backend.config;

import com.managermate.backend.model.Role;
import com.managermate.backend.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initializeRoles() {
        List<String> roleNames = List.of("MANAGER", "EMPLOYEE");

        for (String roleName : roleNames) {
            if (!roleRepository.existsByRoleName(roleName)) {
                Role role = Role.builder().roleName(roleName).build();
                roleRepository.save(role);
                System.out.println("Role created: " + roleName);
            }
        }
    }
}

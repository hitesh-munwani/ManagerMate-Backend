package com.managermate.backend.service;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Role;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.RoleRepository;
import com.managermate.backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User findUserById(Integer id) throws UserNotFoundException {
       User user = userRepository.findById(id).orElse(null);
       if(user == null) {
           throwUserNotFoundWithCustomMessage("User with id %d does not exist", id);
       }
       return user;
    }

    public List<User> getUsersByRoleId(Integer roleId) throws RoleNotFoundException {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not foudn"));
        return userRepository.findByRoleAndIsActiveTrueOrIsActiveNull(role);
    }

    public List<User> getEmployeesByManagerId(Integer managerId) throws UserNotFoundException {
        User user = userRepository.findById(managerId).orElse(null);
        if(user == null) {
            throwUserNotFoundWithCustomMessage("User with id %d does not exist", managerId);
        }
        return userRepository.findByManagerAndIsActiveTrueOrIsActiveNull(user);
    }

    public User getManagerByEmployeeId(Integer userId) throws UserNotFoundException {
        User employee = userRepository.findByUserIdAndIsActiveTrueOrIsActiveNull(userId).orElse(null);
        if(employee == null) {
            throwUserNotFoundWithCustomMessage("Employee with id %d does not exist", userId);
        }
        if(employee.getManager() == null) {
            throwUserNotFoundWithCustomMessage("Manager for employee id %d does not exist", userId);
        }
        return employee;
    }

    public ResponseEntity<User> toggleActiveStatus(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throwUserNotFoundWithCustomMessage("User not found with id ", userId);
        }

        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    private static void throwUserNotFoundWithCustomMessage(String format, Integer userId) throws UserNotFoundException {
        throw new UserNotFoundException(String.format(format, userId));
    }

}

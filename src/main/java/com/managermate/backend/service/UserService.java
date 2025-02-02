package com.managermate.backend.service;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Role;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.RoleRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return userRepository.findByRole(role);
    }

    public List<User> getEmployeesByManagerId(Integer managerId) throws UserNotFoundException {
        User user = userRepository.findById(managerId).orElse(null);
        if(user == null) {
            throwUserNotFoundWithCustomMessage("User with id %d does not exist", managerId);
        }
        return userRepository.findByManager(user);
    }

    public User getManagerByEmployeeId(Integer userId) throws UserNotFoundException {
        User employee = userRepository.findByUserId(userId);
        if(employee == null) {
            throwUserNotFoundWithCustomMessage("Employee with id %d does not exist", userId);
        }
        if(employee.getManager() == null) {
            throwUserNotFoundWithCustomMessage("Manager for employee id %d does not exist", userId);
        }
        return employee;
    }

    private static void throwUserNotFoundWithCustomMessage(String format, Integer userId) throws UserNotFoundException {
        throw new UserNotFoundException(String.format(format, userId));
    }
}

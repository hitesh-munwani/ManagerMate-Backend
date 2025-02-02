package com.managermate.backend.controller;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/details/{userId}")
    User getUserDetails(@PathVariable Integer userId) throws UserNotFoundException {
       return userService.findUserById(userId);
    }

    @GetMapping("/role/{roleId}")
    public List<User> getUsersByRole(@PathVariable Integer roleId) throws RoleNotFoundException {
        return userService.getUsersByRoleId(roleId);
    }

}

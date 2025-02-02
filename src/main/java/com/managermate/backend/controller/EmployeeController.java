package com.managermate.backend.controller;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserService userService;

    @GetMapping("/by-manager/{managerId}")
    public List<User> getEmployeesByManager(@PathVariable Integer managerId) throws UserNotFoundException {
        return userService.getEmployeesByManagerId(managerId);
    }

    @GetMapping("/manager/{userId}")
    public User getManagerByEmployeeId(@PathVariable Integer userId) throws UserNotFoundException {
        return userService.getManagerByEmployeeId(userId);
    }
}

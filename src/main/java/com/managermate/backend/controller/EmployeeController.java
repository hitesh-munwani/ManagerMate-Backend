package com.managermate.backend.controller;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "APIs to manage employee relationships")
public class EmployeeController {

    private final UserService userService;

    @Operation(summary = "Get employees by manager ID", description = "Retrieve a list of employees assigned to a specific manager.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Manager not found",
                    content = @Content)
    })
    @GetMapping("/by-manager/{managerId}")
    public List<User> getEmployeesByManager(@PathVariable Integer managerId) throws UserNotFoundException {
        return userService.getEmployeesByManagerId(managerId);
    }

    @Operation(summary = "Get manager by employee ID", description = "Retrieve the manager of a specific employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the manager",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @GetMapping("/manager/{userId}")
    public User getManagerByEmployeeId(@PathVariable Integer userId) throws UserNotFoundException {
        return userService.getManagerByEmployeeId(userId);
    }

}

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "APIs to manage user data")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/details/{userId}")
    public User getUserDetails(@PathVariable Integer userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }

    @Operation(summary = "Get users by role ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)
    })
    @GetMapping("/role/{roleId}")
    public List<User> getUsersByRole(@PathVariable Integer roleId) throws RoleNotFoundException {
        return userService.getUsersByRoleId(roleId);
    }

    @Operation(summary = "Toggle the active status of a user", description = "This API toggles the active status of a user, setting it to 'active' if it is 'inactive', and vice versa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PutMapping("/toggle-user/active-status")
    public ResponseEntity<User> toggleUserActiveStatus(@RequestParam Integer userId) throws UserNotFoundException {
        return userService.toggleActiveStatus(userId);
    }
}

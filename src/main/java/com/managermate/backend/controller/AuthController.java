package com.managermate.backend.controller;

import com.managermate.backend.dto.ChangePasswordDto;
import com.managermate.backend.dto.LoginResponse;
import com.managermate.backend.dto.LoginUserDto;
import com.managermate.backend.dto.RegisterUserDto;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.security.AuthService;
import com.managermate.backend.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;

    @Operation(summary = "Register a new user", description = "Registers a new user and returns the registered user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided")
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Authenticate user", description = "Authenticates the user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully and JWT token generated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse response = LoginResponse.builder()
                .token(jwtToken)
                .userId(authenticatedUser.getUserId())
                .expiresIn(jwtService.getExpirationTime())
                .role(authenticatedUser.getRole().getRoleName())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change user password", description = "Allows the user to change their password by providing the current and new passwords.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid current password"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) throws UserNotFoundException {
        boolean isPasswordChanged = authenticationService.changePassword(changePasswordDto);

        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("Invalid current password");
        }
    }
}

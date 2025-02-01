package com.managermate.backend.security;

import com.managermate.backend.dto.LoginUserDto;
import com.managermate.backend.dto.RegisterUserDto;
import com.managermate.backend.exception.EmailAlreadyExistsException;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.RoleRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input) {
        rejectIfEmailAlreadyExists(input);
        return userRepository.save(mapToUser(input));
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    private void rejectIfEmailAlreadyExists(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }

    private User mapToUser(RegisterUserDto input) {
        return User.builder()
                .username(input.getUsername())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(roleRepository.findByRoleNameIgnoreCase(input.getRole()))
                .build();
    }
}

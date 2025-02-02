package com.managermate.backend.security;

import com.managermate.backend.dto.ChangePasswordDto;
import com.managermate.backend.dto.LoginUserDto;
import com.managermate.backend.dto.RegisterUserDto;
import com.managermate.backend.exception.EmailAlreadyExistsException;
import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.RoleRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        return userRepository.findByEmailAndIsActiveTrueOrIsActiveNull(input.getEmail())
                .orElseThrow();
    }

    public boolean changePassword(ChangePasswordDto changePasswordDto) throws UserNotFoundException {
        User user = userRepository.findByUserIdAndIsActiveTrueOrIsActiveNull(changePasswordDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            return false;
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user.getUsername());

        userRepository.save(user);

        return true;
    }


    private void rejectIfEmailAlreadyExists(RegisterUserDto input) {
        if (userRepository.existsByEmailAndIsActiveTrueOrIsActiveNull(input.getEmail())) {
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

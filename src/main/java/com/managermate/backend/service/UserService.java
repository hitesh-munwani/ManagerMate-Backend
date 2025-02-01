package com.managermate.backend.service;

import com.managermate.backend.model.User;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(
                new User()
        );
    }

}

package com.managermate.backend.service;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Integer id) throws UserNotFoundException {
       User user = userRepository.findById(id).orElse(null);
       if(user == null) {
           throw new UserNotFoundException(String.format("User with id %d does not exist", id));
       }
       return user;
    }

}

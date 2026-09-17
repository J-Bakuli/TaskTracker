package com.task_tracker.backend.service;

import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials exception"));
    }

    public List<UserEntity> allUserEntities() {
        return userRepository.findAll();
    }
}

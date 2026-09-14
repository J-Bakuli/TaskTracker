package com.task_tracker.backend.service;

import com.task_tracker.backend.dto.SignUpRequest;
import com.task_tracker.backend.dto.UserResponse;
import com.task_tracker.backend.exception.EmailAlreadyExistsException;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(SignUpRequest signUpRequest) {
        String email = signUpRequest.email().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("This email is already taken");
        }

        UserEntity newUserEntity = new UserEntity(email, passwordEncoder.encode(signUpRequest.password()));
        try {
            userRepository.save(newUserEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("This email is already taken", e);
        }

        log.info("Successful sign-up with email={}", newUserEntity.getEmail());
        return new UserResponse(newUserEntity.getId(), newUserEntity.getEmail());
    }
}

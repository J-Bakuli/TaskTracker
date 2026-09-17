package com.task_tracker.backend.service;

import com.task_tracker.backend.dto.SignInRequest;
import com.task_tracker.backend.dto.SignUpRequest;
import com.task_tracker.backend.exception.EmailAlreadyExistsException;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserEntity register(SignUpRequest signUpRequest) {
        String email = signUpRequest.email().trim();
        checkEmailNotTaken(email);
        UserEntity newUserEntity = new UserEntity(email, passwordEncoder.encode(signUpRequest.password()));
        try {
            userRepository.save(newUserEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("This email is already taken", e);
        }

        log.info("Successful sign-up with email={}", newUserEntity.getEmail());
        return new UserEntity(newUserEntity.getId(), newUserEntity.getEmail());
    }

    public UserEntity authenticate(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password())
        );

        return userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new AuthenticationServiceException(
                        String.format("User with email '%s' is not found", signInRequest.email())));
    }

    private void checkEmailNotTaken(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("This email is already taken");
        }
    }
}

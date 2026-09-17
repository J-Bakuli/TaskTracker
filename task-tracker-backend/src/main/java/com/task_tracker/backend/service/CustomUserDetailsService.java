package com.task_tracker.backend.service;

import com.task_tracker.backend.model.CustomUserDetails;
import com.task_tracker.backend.model.UserEntity;
import com.task_tracker.backend.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@Nullable String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("Username is null");
        }
        Optional<UserEntity> userOpt = userRepository.findByEmail(username);
        UserEntity user = userOpt.orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email=%s is not found", userOpt)));
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword());
    }
}
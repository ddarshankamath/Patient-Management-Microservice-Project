package com.darshan.auth_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.darshan.auth_service.model.User;
import com.darshan.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

}

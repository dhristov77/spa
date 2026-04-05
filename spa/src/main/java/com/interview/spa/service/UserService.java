package com.interview.spa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interview.exception.DuplicateResourceException;
import com.interview.exception.ResourceNotFoundException;
import com.interview.spa.dto.UserDTO;
import com.interview.spa.entity.AppUser;
import com.interview.spa.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        validateUserDTO(userDTO);

        try {
            AppUser user = convertToEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return convertToDTO(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }
    }

    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return convertToDTO(user.get());
        } else {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

    }

    private UserDTO convertToDTO(AppUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot convert null User to DTO");
        }

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    private AppUser convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Cannot convert null DTO to User");
        }

        return AppUser.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }

    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User data must not be null");
        }

        if (userDTO.getUsername() == null || userDTO.getUsername()
                .isBlank()) {
            throw new IllegalArgumentException("Username must not be empty");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword()
                .isBlank()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        if (userDTO.getRole() == null) {
            throw new IllegalArgumentException("User role must not be null");
        }
    }

}

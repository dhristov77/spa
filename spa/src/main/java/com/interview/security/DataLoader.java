package com.interview.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.interview.spa.dto.UserDTO;
import com.interview.spa.enums.UserRole;
import com.interview.spa.service.UserService;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        UserDTO admin = UserDTO.builder()
                .username("admin")
                .password("admin")
                .role(UserRole.ADMIN)
                .build();
        userService.createUser(admin);

        UserDTO user = UserDTO.builder()
                .username("user")
                .password("user")
                .role(UserRole.USER)
                .build();
        userService.createUser(user);
    }
}
package com.interview.spa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.interview.exception.InvalidCredentialsException;
import com.interview.security.JwtService;
import com.interview.spa.dto.AuthRequest;
import com.interview.spa.dto.AuthResponse;
import com.interview.spa.dto.UserDTO;
import com.interview.spa.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // allow React
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest request) {
        UserDTO user = userService.findByUsername(request.getUsername());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user);
            return new AuthResponse(token);
        }

        throw new InvalidCredentialsException("Invalid credentials");
    }
}

package com.lostfound.controller;

import com.lostfound.model.User;
import com.lostfound.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import com.lostfound.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("REGISTER API HIT"); // for debug
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            User registeredUser = authService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", "User registered", "user", registeredUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String emailOrUsername = loginRequest.get("emailOrUsername");
        if (emailOrUsername == null || emailOrUsername.isBlank()) {
            emailOrUsername = loginRequest.get("email");
        }
        if (emailOrUsername == null || emailOrUsername.isBlank()) {
            emailOrUsername = loginRequest.get("username");
        }
        String password = loginRequest.get("password");

        if (emailOrUsername == null || emailOrUsername.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email/username and password are required"));
        }

        Optional<User> userOpt = authService.authenticate(emailOrUsername, password);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login successful", "user", userOpt.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email/username or password"));
        }
    }
}

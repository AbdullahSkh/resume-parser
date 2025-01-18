package com.backend.controller;

import com.backend.model.User;
import com.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getPassword() == null || user.getPassword().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email and Password must not be empty.");
                return ResponseEntity.badRequest().body(response);
            }
            authService.registerUser(user.getEmail(), user.getPassword(), User.Role.USER);
            response.put("success", true);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Map<String, Object> loginResponse = new HashMap<>();
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getPassword() == null || user.getPassword().isEmpty()) {
                loginResponse.put("success", false);
                loginResponse.put("message", "Email and Password must not be empty.");
                return ResponseEntity.badRequest().body(loginResponse);
            }

            authService.authenticateUser(user.getEmail(), user.getPassword());
            loginResponse.put("success", true);
            loginResponse.put("message", "Login successful");
            return ResponseEntity.ok(loginResponse);
        } catch(RuntimeException e) {
            loginResponse.put("success", false);
            loginResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(loginResponse);
        }
    }
}

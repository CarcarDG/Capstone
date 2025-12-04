package com.recipe.controller;

import com.recipe.dto.LoginRequest;
import com.recipe.dto.LoginResponse;
import com.recipe.entity.User;
import com.recipe.repository.UserRepository;
import com.recipe.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Public Authentication Controller
 * This controller bypasses Spring Security filters for login
 */
@RestController
@RequestMapping("/public/auth")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RequiredArgsConstructor
public class PublicAuthController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Public login attempt for user: " + request.getUsername());
            
            // Find user
            User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Check password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            
            // Generate token
            String token = jwtUtil.generateToken(user.getUsername());
            
            // Create response
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUser(user);
            
            System.out.println("Login successful for user: " + user.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Public auth endpoint is working");
        response.put("path", "/public/auth");
        return ResponseEntity.ok(response);
    }
}

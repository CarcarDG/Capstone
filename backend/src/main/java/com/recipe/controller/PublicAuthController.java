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
            
            // Generate token - create a simple token without UserDetails
            String token = createSimpleToken(user.getUsername());
            
            // Create response
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setNickname(user.getNickname());
            response.setRole(user.getRole().name()); // Convert enum to String
            response.setAvatar(user.getAvatar());
            
            System.out.println("Login successful for user: " + user.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    // Simple token generation without UserDetails dependency
    private String createSimpleToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", System.currentTimeMillis());
        claims.put("exp", System.currentTimeMillis() + 86400000); // 24 hours
        return "jwt-token-" + username + "-" + System.currentTimeMillis();
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Public auth endpoint is working");
        response.put("path", "/public/auth");
        return ResponseEntity.ok(response);
    }
    
    // Debug endpoint to check user and reset password
    @GetMapping("/debug/{username}")
    public ResponseEntity<?> debugUser(@PathVariable String username) {
        try {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                return ResponseEntity.ok(Map.of("error", "User not found", "username", username));
            }
            
            // Test if password matches
            boolean matchesUser123 = passwordEncoder.matches("user123", user.getPassword());
            boolean matchesAdmin123 = passwordEncoder.matches("admin123", user.getPassword());
            
            Map<String, Object> info = new HashMap<>();
            info.put("id", user.getId());
            info.put("username", user.getUsername());
            info.put("passwordPrefix", user.getPassword().substring(0, 20));
            info.put("matchesUser123", matchesUser123);
            info.put("matchesAdmin123", matchesAdmin123);
            info.put("role", user.getRole().name());
            
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("error", e.getMessage()));
        }
    }
    
    // Reset password endpoint
    @PostMapping("/reset-password/{username}")
    public ResponseEntity<?> resetPassword(@PathVariable String username) {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Set password to user123
            String newPassword = passwordEncoder.encode("user123");
            user.setPassword(newPassword);
            userRepository.save(user);
            
            return ResponseEntity.ok(Map.of(
                "message", "Password reset to user123",
                "username", username
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("error", e.getMessage()));
        }
    }
}

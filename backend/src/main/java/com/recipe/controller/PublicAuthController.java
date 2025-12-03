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

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicAuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println(">>> PUBLIC LOGIN ATTEMPT: " + request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Generate token manually (just for frontend compatibility)
            String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    java.util.Collections.emptyList()));

            return ResponseEntity.ok(new LoginResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getNickname(),
                    user.getRole().name(),
                    user.getAvatar()));
        } else {
            return ResponseEntity.status(401).body("Invalid password");
        }
    }
}

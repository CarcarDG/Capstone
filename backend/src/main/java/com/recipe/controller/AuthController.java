package com.recipe.controller;

import com.recipe.dto.LoginRequest;
import com.recipe.dto.LoginResponse;
import com.recipe.entity.User;
import com.recipe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok(registeredUser);
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        // This would get the current authenticated user from SecurityContext
        // For now, return a placeholder
        return ResponseEntity.ok(new User());
    }
}

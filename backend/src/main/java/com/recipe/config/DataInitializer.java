package com.recipe.config;

import com.recipe.entity.User;
import com.recipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
    }

    private void initializeUsers() {
        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNickname("Administrator");
            admin.setEmail("admin@example.com");
            admin.setRole(User.UserRole.ADMIN);
            admin.setAvatar("https://i.pravatar.cc/200?img=12");
            userRepository.save(admin);
            log.info("Created admin user: admin / admin123");
        }

        // Check if john user exists
        if (userRepository.findByUsername("john").isEmpty()) {
            User john = new User();
            john.setUsername("john");
            john.setPassword(passwordEncoder.encode("user123"));
            john.setNickname("John Doe");
            john.setEmail("john@example.com");
            john.setRole(User.UserRole.USER);
            john.setAvatar("https://i.pravatar.cc/200?img=33");
            userRepository.save(john);
            log.info("Created user: john / user123");
        }

        // Check if jane user exists
        if (userRepository.findByUsername("jane").isEmpty()) {
            User jane = new User();
            jane.setUsername("jane");
            jane.setPassword(passwordEncoder.encode("user123"));
            jane.setNickname("Jane Smith");
            jane.setEmail("jane@example.com");
            jane.setRole(User.UserRole.USER);
            jane.setAvatar("https://i.pravatar.cc/200?img=47");
            userRepository.save(jane);
            log.info("Created user: jane / user123");
        }

        log.info("Data initialization completed");
    }
}

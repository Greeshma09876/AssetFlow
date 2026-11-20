package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Create roles if they do not exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() ->
                            roleRepository.save(new Role("ROLE_ADMIN")));

            Role staffRole = roleRepository.findByName("ROLE_STAFF")
                    .orElseGet(() ->
                            roleRepository.save(new Role("ROLE_STAFF")));

            // Read passwords from environment variables
            String adminPassword = System.getenv("ADMIN_PASSWORD");
            String staffPassword = System.getenv("STAFF_PASSWORD");

            // Create initial Admin account
            if (!userRepository.existsByUsername("admin")) {

                if (adminPassword == null || adminPassword.isBlank()) {
                    throw new IllegalStateException(
                            "ADMIN_PASSWORD is not configured");
                }

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@assetflow.com");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(adminRole);
                admin.setEnabled(true);

                userRepository.save(admin);
            }

            // Create initial Staff account
            if (!userRepository.existsByUsername("staff")) {

                if (staffPassword == null || staffPassword.isBlank()) {
                    throw new IllegalStateException(
                            "STAFF_PASSWORD is not configured");
                }

                User staff = new User();
                staff.setUsername("staff");
                staff.setEmail("staff@assetflow.com");
                staff.setPassword(passwordEncoder.encode(staffPassword));
                staff.setRole(staffRole);
                staff.setEnabled(true);

                userRepository.save(staff);
            }
        };
    }
}
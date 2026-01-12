package com.avidata.api.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.avidata.api.infrastructure.adapter.out.persistence.UserJpaRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;

/**
 * Data Initializer
 * Creates default users on application startup (for development/testing)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // Only create users if database is empty
            if (userRepository.count() == 0) {
                log.info("Initializing database with default users...");
                
                UserEntity user = UserEntity.builder()
                        .username("user")
                        .password(passwordEncoder.encode("password"))
                        .email("user@servpro.com")
                        .roles("USER")
                        .enabled(true)
                        .build();
                
                UserEntity admin = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@servpro.com")
                        .roles("USER,ADMIN")
                        .enabled(true)
                        .build();
                
                userRepository.save(user);
                userRepository.save(admin);
                
                log.info("Default users created: user/password and admin/admin");
            }
        };
    }
}

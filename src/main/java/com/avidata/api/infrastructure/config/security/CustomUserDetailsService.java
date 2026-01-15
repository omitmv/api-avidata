package com.avidata.api.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avidata.api.infrastructure.adapter.out.persistence.UserJpaRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom UserDetailsService
 * Loads user data from database
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserJpaRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        // Convert roles string to authorities
        // Se roles for null ou vazio, usar role padrão USER
        String rolesStr = userEntity.getRoles();
        if (rolesStr == null || rolesStr.trim().isEmpty()) {
            rolesStr = "USER";
        }
        
        List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                .collect(Collectors.toList());
        
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!userEntity.getEnabled())
                .build();
    }
}

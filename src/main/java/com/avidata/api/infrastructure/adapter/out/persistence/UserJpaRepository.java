package com.avidata.api.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Users
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String username);
    
    List<UserEntity> findAllByEnabledTrue();
    
    void deleteByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

    List<UserEntity> findByUsernameContainingAndEnabledTrue(String username);
}

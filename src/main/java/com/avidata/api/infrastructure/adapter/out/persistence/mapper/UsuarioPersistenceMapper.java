package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;

@Component
public class UsuarioPersistenceMapper {

  public UserEntity toEntity(Usuario usuario) {
    return UserEntity.builder()
        .id(usuario.getId())
        .username(usuario.getUsername())
        .email(usuario.getEmail())
        .password(usuario.getPassword())
        .createdAt(usuario.getCreatedAt())
        .updatedAt(usuario.getUpdatedAt())
        .enabled(usuario.getEnabled())
        .roles(usuario.getRoles())
        .build();
  }

  public Usuario toDomain(UserEntity entity) {
    return Usuario.builder()
        .id(entity.getId())
        .username(entity.getUsername())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .enabled(entity.getEnabled())
        .roles(entity.getRoles())
        .build();
  }
}

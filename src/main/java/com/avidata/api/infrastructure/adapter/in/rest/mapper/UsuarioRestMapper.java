package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioResponse;

public class UsuarioRestMapper {
  
  public Usuario toDomain(UsuarioRequest request) {
    return Usuario.builder()
        .username(request.getLogin())
        .email(request.getEmail())
        .password(request.getSenha())
        .enabled(request.getEnabled() == null ? true : request.getEnabled())
        .roles(request.getRoles() == null ? "USER" : request.getRoles())
        .build();
  }
  
  public UsuarioResponse toResponse(Usuario usuario) {
    return UsuarioResponse.builder()
        .id(usuario.getId())
        .username(usuario.getUsername())
        .email(usuario.getEmail())
        .createdAt(usuario.getCreatedAt())
        .updatedAt(usuario.getUpdatedAt())
        .enabled(usuario.getEnabled())
        .roles(usuario.getRoles())
        .build();
  }
}

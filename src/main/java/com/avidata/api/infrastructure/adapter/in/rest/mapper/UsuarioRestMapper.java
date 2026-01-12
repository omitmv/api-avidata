package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioRequest;

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
  
}

package com.avidata.api.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
  private Long id;
  private String username;
  private String roles;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean enabled;
}

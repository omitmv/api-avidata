package com.avidata.api.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
  private Long id;
  private String username;
  private String password;
  private String roles;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean enabled;
}

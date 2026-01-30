package com.avidata.api.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EspecieRequest {
  private String classe;
  private String familia;
  private String genero;
  private String especie;
  @NotNull
  @NotBlank(message = "O campo nomePopular é obrigatório e não pode ser duplicado.")
  private String nomePopular;
}

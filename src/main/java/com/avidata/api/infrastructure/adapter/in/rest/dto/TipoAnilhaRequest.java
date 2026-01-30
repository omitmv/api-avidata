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
public class TipoAnilhaRequest {
  @NotNull
  @NotBlank(message = "O campo tipoAnilha é obrigatório.")
  private String tipoAnilha;
  @NotNull
  @NotBlank(message = "O campo flTipoAnilha é obrigatório e não pode ser duplicado.")
  private String flTipoAnilha;
  private String descricao;
}

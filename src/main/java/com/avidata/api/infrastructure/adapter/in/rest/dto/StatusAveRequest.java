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
public class StatusAveRequest {
  @NotNull
  @NotBlank(message = "O campo statusAve é obrigatório.")
  private String statusAve;
  @NotNull
  @NotBlank(message = "O campo flStatusAve é obrigatório e não pode ser duplicado.")
  private String flStatusAve;
  private String descricao;
}

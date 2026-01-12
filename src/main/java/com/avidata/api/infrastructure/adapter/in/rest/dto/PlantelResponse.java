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
public class PlantelResponse {
  private Long id;
  private String nome;
  private String descricao;
  private LocalDateTime dataCadastro;
}

package com.avidata.api.infrastructure.adapter.in.rest.dto;

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
  private String tipoAnilha;
  private String flTipoAnilha;
  private String descricao;
}

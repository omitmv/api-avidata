package com.avidata.api.domain.model;

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
public class TipoAnilha {
  private Long id;
  private String tipoAnilha;
  private String flTipoAnilha;
  private String descricao;
}

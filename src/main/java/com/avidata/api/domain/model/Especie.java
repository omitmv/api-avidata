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
public class Especie {
  private Long id;
  private String classe;
  private String familia;
  private String genero;
  private String especie;
  private String nomePopular;
}

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
public class EspeciesResponse {
  private Long id;
  private String classe;
  private String familia;
  private String genero;
  private String especie;
  private String nomePopular;
}

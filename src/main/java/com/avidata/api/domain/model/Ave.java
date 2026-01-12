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
public class Ave {
  private Long id;
  private String nome;
  private String sexo;
  private Especie especie;
  private Plantel plantel;
  private String identificador;
  private String numeroAnilha;
  private String anilhaTipo;
  private Integer anoAnilha;
  private String status;
  private String cor;
  private Ave pai;
  private Ave mae;
}

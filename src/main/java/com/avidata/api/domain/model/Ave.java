package com.avidata.api.domain.model;

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
public class Ave {
  private Long id;
  private String identificador;
  private String nome;
  private Especie especie;
  private String sexo;
  private LocalDateTime dataNascimento;
  private String cor;
  private String numeroAnilha;
  private TipoAnilha tipoAnilha;
  private Integer anoAnilha;
  private Ave pai;
  private Ave mae;
  private Plantel plantel;
  private StatusAve statusAve;
  private LocalDateTime dataEntrada;
  private LocalDateTime dataSaida;
  private String observacoes;
}

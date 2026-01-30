package com.avidata.api.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.model.Plantel;
import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.domain.model.TipoAnilha;

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
public class AveResponse {
    private Long id;
    private String identificador;
    private String nome;
    private Especie especie;
    private String sexo;
    private String cor;
    private String numeroAnilha;
    private TipoAnilha tipoAnilha;
    private Integer anoAnilha;
    private Ave pai;
    private Ave mae;
    private Plantel plantel;
    private StatusAve statusAve;
    private LocalDateTime dataNascimento;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String observacoes;
}

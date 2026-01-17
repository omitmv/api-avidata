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
public class AveResponse {
    private Long id;
    private String identificador;
    private String nome;
    private Long especieId;
    private String especieNome;
    private String sexo;
    private String cor;
    private String numeroAnilha;
    private String anilhaTipo;
    private Integer anoAnilha;
    private Long paiId;
    private String paiNome;
    private Long maeId;
    private String maeNome;
    private Long plantelId;
    private String plantelNome;
    private String status;
}

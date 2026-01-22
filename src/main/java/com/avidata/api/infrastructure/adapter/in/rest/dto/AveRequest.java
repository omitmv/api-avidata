package com.avidata.api.infrastructure.adapter.in.rest.dto;

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
public class AveRequest {
    private String identificador;
    private String nome;
    private Long especieId;
    private String sexo; // M, F, Indefinido
    private String cor;
    private String numeroAnilha;
    private TipoAnilha tipoAnilha; // SISPASS, FOB, Outros
    private Integer anoAnilha;
    private Long paiId;
    private Long maeId;
    private Long plantelId;
    private StatusAve statusAve; // Ativo, Vendido, Morto, Emprestado, Perdido
}

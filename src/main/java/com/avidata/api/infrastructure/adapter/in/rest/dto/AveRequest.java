package com.avidata.api.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @NotBlank(message = "O identificador é obrigatório")
    @Size(max = 50, message = "O identificador deve ter no máximo 50 caracteres")
    private String identificador;
    
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @NotNull(message = "Selecione uma espécie válida")
    private Long especieId;
    
    @NotBlank(message = "O campo sexo é obrigatório")
    private String sexo; // M, F, I (Indefinido)
    
    private String cor;
    
    @Size(max = 50, message = "O número da anilha deve ter no máximo 50 caracteres")
    private String numeroAnilha;
    
    private Long tipoAnilhaId;
    private Integer anoAnilha;
    private Long paiId;
    private Long maeId;
    
    @NotNull(message = "Selecione um plantel válido")
    private Long plantelId;
    
    @NotNull(message = "Selecione um status válido")
    private Long statusId;
    
    @NotBlank(message = "O campo data de nascimento é obrigatório")
    private String dataNascimento; // ISO 8601 format
    
    private String dataEntrada;    // ISO 8601 format
    private String dataSaida;      // ISO 8601 format
    
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres")
    private String observacoes;
}

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
public class Plantel {
  private Long id;
  private String nome;
  private String descricao;
  private LocalDateTime dataCadastro;
  private byte[] arquivoLogo;
  private String linkLogo;
  private String nomeArquivoLogo;
}

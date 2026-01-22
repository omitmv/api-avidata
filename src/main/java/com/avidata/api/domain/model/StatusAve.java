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
public class StatusAve {
  private Long id;
  private String statusAve;
  private String flStatusAve;
  private String descricao;
}

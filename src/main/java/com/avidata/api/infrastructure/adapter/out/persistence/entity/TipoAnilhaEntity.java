package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipoAnilha")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoAnilhaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "tipo_anilha")
  private String tipoAnilha;
  @Column(name = "fl_tipo_anilha")
  private String flTipoAnilha;
  @Column(name = "descricao")
  private String descricao;
}

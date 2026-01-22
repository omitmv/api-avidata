package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plantel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantelEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String descricao;
  @Column(name = "data_cadastro")
  private LocalDateTime dataCadastro;
}

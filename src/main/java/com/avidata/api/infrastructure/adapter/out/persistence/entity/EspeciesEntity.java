package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspeciesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String classe;
  private String familia;
  private String genero;
  private String especie;
  @Column(name = "nome_popular")
  private String nomePopular;
  @Column(name = "link_foto")
  private String linkFoto;
}

package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import com.avidata.api.domain.enumerador.SexoEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aves")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "plantel_id")
  private PlantelEntity plantel;
  private String identificador;
  private String nome;
  @ManyToOne
  @JoinColumn(name = "especie_id")
  private EspeciesEntity especie;
  @Column(nullable = false)
  private String sexo = SexoEnum.INDEFINIDO.getCodigo();
  @Column(name = "data_nascimento")
  private LocalDateTime dataNascimento;
  private String cor;
  @Column(name = "numero_anilha")
  private String numeroAnilha;
  @ManyToOne
  @JoinColumn(name = "tipo_anilha_id")
  private TipoAnilhaEntity tipoAnilha;
  @Column(name = "ano_anilha")
  private Integer anoAnilha;
  @ManyToOne
  @JoinColumn(name = "pai_id")
  private AvesEntity pai;
  @ManyToOne
  @JoinColumn(name = "mae_id")
  private AvesEntity mae;
  @ManyToOne
  @JoinColumn(name = "status_id")
  private StatusAveEntity statusAve;
  @Column(name = "data_entrada")
  private LocalDateTime dataEntrada;
  @Column(name = "data_saida")
  private LocalDateTime dataSaida;
  private String observacoes;
}

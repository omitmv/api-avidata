package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import com.avidata.api.domain.enumerador.SexoEnum;
import com.avidata.api.domain.enumerador.StatusEnum;
import com.avidata.api.domain.enumerador.TipoAnilhaEnum;

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
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private SexoEnum sexo = SexoEnum.INDEFINIDO;
  private LocalDateTime dataNascimento;
  private String cor;
  private String numeroAnilha;
  @Enumerated(EnumType.STRING)
  @Column(nullable = true)
  private TipoAnilhaEnum anilhaTipo;
  private Integer anoAnilha;
  @ManyToOne
  @JoinColumn(name = "pai_id")
  private AvesEntity pai;
  @ManyToOne
  @JoinColumn(name = "mae_id")
  private AvesEntity mae;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusEnum status = StatusEnum.ATIVO;
  private LocalDateTime dataEntrada;
  private LocalDateTime dataSaida;
  private String observacoes;
}

package com.avidata.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statusAve")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusAveEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "status_ave")
  private String statusAve;
  @Column(name = "fl_status_ave")
  private String flStatusAve;
  @Column(name = "descricao")
  private String descricao;
}

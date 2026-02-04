package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.PlantelEntity;

/**
 * Mapper para conversão entre entidade de domínio Plantel e entidade de persistência PlantelEntity
 */
@Component
public class PlantelPersistenceMapper {

  /**
   * Converte a entidade de domínio Plantel para entidade de persistência PlantelEntity
   * 
   * @param plantel entidade de domínio
   * @return entidade de persistência
   */
  public PlantelEntity toEntity(Plantel plantel) {
    if (plantel == null) {
      return null;
    }

    return PlantelEntity.builder()
        .id(plantel.getId())
        .nome(plantel.getNome())
        .descricao(plantel.getDescricao())
        .dataCadastro(plantel.getDataCadastro())
        .linkLogo(plantel.getLinkLogo())
        .build();
  }

  /**
   * Converte a entidade de persistência PlantelEntity para entidade de domínio Plantel
   * 
   * @param entity entidade de persistência
   * @return entidade de domínio
   */
  public Plantel toDomain(PlantelEntity entity) {
    if (entity == null) {
      return null;
    }

    return Plantel.builder()
        .id(entity.getId())
        .nome(entity.getNome())
        .descricao(entity.getDescricao())
        .dataCadastro(entity.getDataCadastro())
        .linkLogo(entity.getLinkLogo())
        .build();
  }
}

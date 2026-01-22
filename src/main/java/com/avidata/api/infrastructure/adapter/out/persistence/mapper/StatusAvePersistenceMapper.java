package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.StatusAveEntity;

@Component
public class StatusAvePersistenceMapper {
  public StatusAveEntity toEntity(StatusAve statusAve) {
    if (statusAve == null) {
      return null;
    }

    return StatusAveEntity.builder()
      .id(statusAve.getId())
      .statusAve(statusAve.getStatusAve())
      .flStatusAve(statusAve.getFlStatusAve())
      .descricao(statusAve.getDescricao())
      .build();
  }

  public StatusAve toDomain(StatusAveEntity entity) {
    if (entity == null) {
      return null;
    }

    return StatusAve.builder()
      .id(entity.getId())
      .statusAve(entity.getStatusAve())
      .flStatusAve(entity.getFlStatusAve())
      .descricao(entity.getDescricao())
      .build();
  }
}

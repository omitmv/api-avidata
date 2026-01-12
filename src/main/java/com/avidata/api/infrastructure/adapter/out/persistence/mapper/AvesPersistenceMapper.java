package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.AvesEntity;

@Component
public class AvesPersistenceMapper {
  public AvesEntity toEntity(Ave ave) {
    if (ave == null) {
      return null;
    }

    return AvesEntity.builder()
        .nome(ave.getNome())
        .especie(new EspeciesPersistenceMapper().toEntity(ave.getEspecie()))
        .plantel(new PlantelPersistenceMapper().toEntity(ave.getPlantel()))
        .cor(ave.getCor())
        .numeroAnilha(ave.getNumeroAnilha())
        .anoAnilha(ave.getAnoAnilha())
        .pai(ave.getPai() != null ? toEntity(ave.getPai()) : null)
        .mae(ave.getMae() != null ? toEntity(ave.getMae()) : null)
        .build();
  }

  public Ave toDomain(AvesEntity entity) {
    if (entity == null) {
      return null;
    }

    return Ave.builder()
        .nome(entity.getNome())
        .especie(new EspeciesPersistenceMapper().toDomain(entity.getEspecie()))
        .plantel(new PlantelPersistenceMapper().toDomain(entity.getPlantel()))
        .cor(entity.getCor())
        .numeroAnilha(entity.getNumeroAnilha())
        .anoAnilha(entity.getAnoAnilha())
        .pai(entity.getPai() != null ? toDomain(entity.getPai()) : null)
        .mae(entity.getMae() != null ? toDomain(entity.getMae()) : null)
        .build();
  }
}

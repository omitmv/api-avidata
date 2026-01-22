package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.TipoAnilhaEntity;

@Component
public class TipoAnilhaPersistenceMapper {
  public TipoAnilhaEntity toEntity(TipoAnilha tipoAnilha) {
    if (tipoAnilha == null) {
      return null;
    }

    return TipoAnilhaEntity.builder()
        .id(tipoAnilha.getId())
        .flTipoAnilha(tipoAnilha.getFlTipoAnilha())
        .tipoAnilha(tipoAnilha.getTipoAnilha())
        .descricao(tipoAnilha.getDescricao())
        .build();
  }

  public TipoAnilha toDomain(TipoAnilhaEntity entity) {
    if (entity == null) {
      return null;
    }

    return TipoAnilha.builder()
        .id(entity.getId())
        .flTipoAnilha(entity.getFlTipoAnilha())
        .tipoAnilha(entity.getTipoAnilha())
        .descricao(entity.getDescricao())
        .build();
  }
}

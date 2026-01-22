package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.infrastructure.adapter.in.rest.dto.TipoAnilhaRequest;

@Component
public class TipoAnilhaRestMapper {
  public TipoAnilha toDomain(TipoAnilhaRequest request) {
    if (request == null) {
      return null;
    }

    return TipoAnilha.builder()
        .tipoAnilha(request.getTipoAnilha())
        .flTipoAnilha(request.getFlTipoAnilha())
        .descricao(request.getDescricao())
        .build();
  }
}

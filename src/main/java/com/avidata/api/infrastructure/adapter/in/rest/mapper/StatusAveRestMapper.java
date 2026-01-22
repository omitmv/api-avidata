package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.infrastructure.adapter.in.rest.dto.StatusAveRequest;

@Component
public class StatusAveRestMapper {
  public StatusAve toDomain(StatusAveRequest request) {
    if (request == null) {
      return null;
    }

    return StatusAve.builder()
        .statusAve(request.getStatusAve())
        .flStatusAve(request.getFlStatusAve())
        .descricao(request.getDescricao())
        .build();
  }
}

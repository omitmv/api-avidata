package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelResponse;

@Component
public class PlantelRestMapper {
  public Plantel toDomain(PlantelRequest request) {
    return Plantel.builder()
        .nome(request.getNome())
        .descricao(request.getDescricao())
        .arquivoLogo(request.getArquivoLogo())
        .linkLogo(request.getLinkLogo())
        .nomeArquivoLogo(request.getNomeArquivoLogo())
        .build();
  }

  public PlantelResponse toResponse(Plantel plantel) {
    return PlantelResponse.builder()
        .id(plantel.getId())
        .nome(plantel.getNome())
        .descricao(plantel.getDescricao())
        .dataCadastro(plantel.getDataCadastro())
        .linkLogo(plantel.getLinkLogo())
        .build();
  }
}

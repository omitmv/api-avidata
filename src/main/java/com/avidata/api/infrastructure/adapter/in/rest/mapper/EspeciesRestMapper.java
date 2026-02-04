package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Especie;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspecieRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspeciesResponse;

@Component
public class EspeciesRestMapper {
  public Especie toDomain(EspecieRequest request) {
    return Especie.builder()
        .classe(request.getClasse())
        .familia(request.getFamilia())
        .genero(request.getGenero())
        .especie(request.getEspecie())
        .nomePopular(request.getNomePopular())
        .build();
  }

  public EspeciesResponse toResponse(Especie especie) {
    return EspeciesResponse.builder()
        .id(especie.getId())
        .classe(especie.getClasse())
        .familia(especie.getFamilia())
        .genero(especie.getGenero())
        .especie(especie.getEspecie())
        .nomePopular(especie.getNomePopular())
        .linkFoto(especie.getLinkFoto())
        .build();
  }
}

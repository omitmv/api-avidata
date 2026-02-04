package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Especie;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.EspeciesEntity;

/**
 * Mapper para conversão entre entidade de domínio Especie e entidade de persistência EspeciesEntity
 */
@Component
public class EspeciesPersistenceMapper {

  /**
   * Converte a entidade de domínio Especie para entidade de persistência EspeciesEntity
   * 
   * @param especie entidade de domínio
   * @return entidade de persistência
   */
  public EspeciesEntity toEntity(Especie especie) {
    if (especie == null) {
      return null;
    }

    return EspeciesEntity.builder()
        .id(especie.getId())
        .classe(especie.getClasse())
        .familia(especie.getFamilia())
        .genero(especie.getGenero())
        .especie(especie.getEspecie())
        .nomePopular(especie.getNomePopular())
        .linkFoto(especie.getLinkFoto())
        .build();
  }

  /**
   * Converte a entidade de persistência EspeciesEntity para entidade de domínio Especie
   * 
   * @param entity entidade de persistência
   * @return entidade de domínio
   */
  public Especie toDomain(EspeciesEntity entity) {
    if (entity == null) {
      return null;
    }

    return Especie.builder()
        .id(entity.getId())
        .classe(entity.getClasse())
        .familia(entity.getFamilia())
        .genero(entity.getGenero())
        .especie(entity.getEspecie())
        .nomePopular(entity.getNomePopular())
        .linkFoto(entity.getLinkFoto())
        .build();
  }
}

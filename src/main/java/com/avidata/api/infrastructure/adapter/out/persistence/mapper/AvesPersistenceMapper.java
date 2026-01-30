package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.AvesEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.EspeciesEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.PlantelEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.StatusAveEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.TipoAnilhaEntity;

@Component
public class AvesPersistenceMapper {
  public AvesEntity toEntity(Ave ave) {
    if (ave == null) {
      return null;
    }

    AvesEntity.AvesEntityBuilder builder = AvesEntity.builder()
        .id(ave.getId())
        .identificador(ave.getIdentificador())
        .nome(ave.getNome())
        .cor(ave.getCor())
        .numeroAnilha(ave.getNumeroAnilha())
        .anoAnilha(ave.getAnoAnilha())
        .dataNascimento(ave.getDataNascimento())
        .dataEntrada(ave.getDataEntrada())
        .dataSaida(ave.getDataSaida())
        .observacoes(ave.getObservacoes());

    if(ave.getSexo() != null) {
      builder.sexo(ave.getSexo());
    }
    // Set relationships - only with IDs to avoid transient object exceptions
    if (ave.getEspecie() != null && ave.getEspecie().getId() != null) {
      builder.especie(EspeciesEntity.builder().id(ave.getEspecie().getId()).build());
    }
    
    if (ave.getPlantel() != null && ave.getPlantel().getId() != null) {
      builder.plantel(PlantelEntity.builder().id(ave.getPlantel().getId()).build());
    }
    
    if (ave.getPai() != null && ave.getPai().getId() != null) {
      builder.pai(AvesEntity.builder().id(ave.getPai().getId()).build());
    }
    
    if (ave.getMae() != null && ave.getMae().getId() != null) {
      builder.mae(AvesEntity.builder().id(ave.getMae().getId()).build());
    }
    
    if (ave.getStatusAve() != null && ave.getStatusAve().getId() != null) {
      builder.statusAve(StatusAveEntity.builder().id(ave.getStatusAve().getId()).build());
    }
    
    if (ave.getTipoAnilha() != null && ave.getTipoAnilha().getId() != null) {
      builder.tipoAnilha(TipoAnilhaEntity.builder().id(ave.getTipoAnilha().getId()).build());
    }

    return builder.build();
  }

  public Ave toDomain(AvesEntity entity) {
    if (entity == null) {
      return null;
    }

    Ave.AveBuilder builder = Ave.builder()
        .id(entity.getId())
        .identificador(entity.getIdentificador())
        .nome(entity.getNome())
        .cor(entity.getCor())
        .numeroAnilha(entity.getNumeroAnilha())
        .anoAnilha(entity.getAnoAnilha())
        .dataNascimento(entity.getDataNascimento())
        .dataEntrada(entity.getDataEntrada())
        .dataSaida(entity.getDataSaida())
        .observacoes(entity.getObservacoes());

    // Convert sexo string to enum
    if (entity.getSexo() != null) {
      builder.sexo(entity.getSexo());
    }
    // Convert relationships
    if (entity.getEspecie() != null) {
      builder.especie(new EspeciesPersistenceMapper().toDomain(entity.getEspecie()));
    }
    
    if (entity.getPlantel() != null) {
      builder.plantel(new PlantelPersistenceMapper().toDomain(entity.getPlantel()));
    }
    
    // For pai and mae, only set ID to avoid circular references and performance issues
    if (entity.getPai() != null) {
      builder.pai(Ave.builder()
          .id(entity.getPai().getId())
          .identificador(entity.getPai().getIdentificador())
          .nome(entity.getPai().getNome())
          .build());
    }
    
    if (entity.getMae() != null) {
      builder.mae(Ave.builder()
          .id(entity.getMae().getId())
          .identificador(entity.getMae().getIdentificador())
          .nome(entity.getMae().getNome())
          .build());
    }
    
    if (entity.getStatusAve() != null) {
      builder.statusAve(new StatusAvePersistenceMapper().toDomain(entity.getStatusAve()));
    }
    
    if (entity.getTipoAnilha() != null) {
      builder.tipoAnilha(new TipoAnilhaPersistenceMapper().toDomain(entity.getTipoAnilha()));
    }

    return builder.build();
  }
}

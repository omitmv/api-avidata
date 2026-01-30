package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.enumerador.SexoEnum;
import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.model.Plantel;
import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AveRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AveResponse;

/**
 * REST Mapper - Converts between REST DTOs and Domain Models
 * Input Adapter responsibility
 */
@Component
public class AveRestMapper {
    
    public Ave toDomain(AveRequest request) {
        if (request == null) {
            return null;
        }
        
        Ave.AveBuilder builder = Ave.builder()
                .identificador(request.getIdentificador())
                .nome(request.getNome())
                .cor(request.getCor())
                .numeroAnilha(request.getNumeroAnilha())
                .anoAnilha(request.getAnoAnilha());
        
        if (request.getDataNascimento() != null) {
            builder.dataNascimento(LocalDate.parse(request.getDataNascimento()).atStartOfDay());
        }
        if (request.getDataEntrada() != null) {
            builder.dataEntrada(LocalDate.parse(request.getDataEntrada()).atStartOfDay());
        }
        if (request.getDataSaida() != null) {
            builder.dataSaida(LocalDate.parse(request.getDataSaida()).atStartOfDay());
        }
        if (request.getObservacoes() != null) {
            builder.observacoes(request.getObservacoes());
        }
        // Convert sexo string to enum
        if (request.getSexo() != null) {
            try {
                builder.sexo(request.getSexo());
            } catch (IllegalArgumentException e) {
                builder.sexo(SexoEnum.INDEFINIDO.getCodigo());
            }
        }
        
        Ave ave = builder.build();
        
        // Set relationships with IDs only
        if (request.getEspecieId() != null) {
            ave.setEspecie(Especie.builder().id(request.getEspecieId()).build());
        }
        if (request.getPlantelId() != null) {
            ave.setPlantel(Plantel.builder().id(request.getPlantelId()).build());
        }
        if (request.getPaiId() != null) {
            ave.setPai(Ave.builder().id(request.getPaiId()).build());
        }
        if (request.getMaeId() != null) {
            ave.setMae(Ave.builder().id(request.getMaeId()).build());
        }
        if (request.getTipoAnilhaId() != null) {
            ave.setTipoAnilha(TipoAnilha.builder().id(request.getTipoAnilhaId()).build());
        }
        if (request.getStatusId() != null) {
            ave.setStatusAve(StatusAve.builder().id(request.getStatusId()).build());
        }
        
        return ave;
    }
    
    public AveResponse toResponse(Ave ave) {
        if (ave == null) {
            return null;
        }
        
        AveResponse.AveResponseBuilder builder = AveResponse.builder()
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
        
        // Convert sexo enum to string
        if (ave.getSexo() != null) {
            builder.sexo(ave.getSexo());
        }
        
        AveResponse response = builder.build();
        
        // Extract IDs and names from relationships
        if (ave.getEspecie() != null) {
            response.setEspecie(ave.getEspecie());
        }
        
        if (ave.getPlantel() != null) {
            response.setPlantel(ave.getPlantel());
        }
        
        if (ave.getPai() != null) {
            response.setPai(ave.getPai());
        }
        
        if (ave.getMae() != null) {
            response.setMae(ave.getMae());
        }

        if (ave.getTipoAnilha() != null) {
            response.setTipoAnilha(ave.getTipoAnilha());
        }
        
        if (ave.getStatusAve() != null) {
            response.setStatusAve(ave.getStatusAve());
        }
        
        return response;
    }
    
    public List<AveResponse> toResponseList(List<Ave> aves) {
        if (aves == null) {
            return null;
        }
        return aves.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

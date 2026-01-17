package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.model.Plantel;
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
        
        Ave ave = Ave.builder()
                .identificador(request.getIdentificador())
                .nome(request.getNome())
                .sexo(request.getSexo())
                .cor(request.getCor())
                .numeroAnilha(request.getNumeroAnilha())
                .anilhaTipo(request.getAnilhaTipo())
                .anoAnilha(request.getAnoAnilha())
                .status(request.getStatus())
                .build();
        
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
        
        return ave;
    }
    
    public AveResponse toResponse(Ave ave) {
        if (ave == null) {
            return null;
        }
        
        AveResponse response = AveResponse.builder()
                .id(ave.getId())
                .identificador(ave.getIdentificador())
                .nome(ave.getNome())
                .sexo(ave.getSexo())
                .cor(ave.getCor())
                .numeroAnilha(ave.getNumeroAnilha())
                .anilhaTipo(ave.getAnilhaTipo())
                .anoAnilha(ave.getAnoAnilha())
                .status(ave.getStatus())
                .build();
        
        // Extract IDs and names from relationships
        if (ave.getEspecie() != null) {
            response.setEspecieId(ave.getEspecie().getId());
            response.setEspecieNome(ave.getEspecie().getNomePopular());
        }
        
        if (ave.getPlantel() != null) {
            response.setPlantelId(ave.getPlantel().getId());
            response.setPlantelNome(ave.getPlantel().getNome());
        }
        
        if (ave.getPai() != null) {
            response.setPaiId(ave.getPai().getId());
            response.setPaiNome(ave.getPai().getNome());
        }
        
        if (ave.getMae() != null) {
            response.setMaeId(ave.getMae().getId());
            response.setMaeNome(ave.getMae().getNome());
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

package com.avidata.api.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.domain.port.in.IPlantelUseCase;
import com.avidata.api.domain.port.out.IPlantelRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Application Service - Plantel Business Logic
 * Implements use cases using domain entities and ports
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PlantelService implements IPlantelUseCase {
    
    private final IPlantelRepository plantelRepository;
    
    @Override
    public Plantel criarPlantel(Plantel plantel) {
        log.info("[DEBUG] Criando plantel: {}", plantel);
        plantel.setDataCadastro(LocalDateTime.now());
        return plantelRepository.save(plantel);
    }
    
    @Override
    public List<Plantel> listarPlantels(String nome) {
      log.info("[DEBUG] Listando plantels com nome: {}", nome);
      if (nome != null && !nome.isEmpty()) {
          return plantelRepository.findByNomeContaining(nome);
      } else {
          return plantelRepository.findAll();
      }
    }

    @Override
    public Optional<Plantel> obterPlantelPorId(Long id) {
        log.info("[DEBUG] Buscando plantel por id: {}", id);
        return plantelRepository.findById(id);
    }

    @Override
    public Plantel atualizarPlantel(Long id, Plantel plantel) {
        log.info("[DEBUG] Atualizando plantel com id: {}", id);
        return plantelRepository.findById(id)
                .map(plantelExistente -> {
                    plantelExistente.setNome(plantel.getNome());
                    plantelExistente.setDescricao(plantel.getDescricao());
                    return plantelRepository.save(plantelExistente);
                })
                .orElseThrow(() -> new RuntimeException("Plantel não encontrado com id: " + id));
    }
    
    @Override
    public void deletarPlantel(Long id) {
        log.info("[DEBUG] Deletando plantel com id: {}", id);
        if (!plantelRepository.existsById(id)) {
            log.error("[ERROR] Plantel não encontrado com id: {}", id);
            throw new RuntimeException("Plantel não encontrado com id: " + id);
        }
        plantelRepository.deleteById(id);
    }
}

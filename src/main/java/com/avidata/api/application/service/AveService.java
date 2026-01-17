package com.avidata.api.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.port.in.IAveUseCase;
import com.avidata.api.domain.port.out.IAvesRepository;

import java.util.List;
import java.util.Optional;

/**
 * Application Service - Ave Business Logic
 * Implements use cases using domain entities and ports
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AveService implements IAveUseCase {
    
    private final IAvesRepository avesRepository;
    
    @Override
    public Ave criarAve(Ave ave) {
        log.info("[DEBUG] Criando ave: {}", ave);
        return avesRepository.save(ave);
    }
    
    @Override
    public List<Ave> listarAves(String nome) {
        log.info("[DEBUG] Listando aves com nome: {}", nome);
        if (nome != null && !nome.isEmpty()) {
            return avesRepository.findByNomeContaining(nome);
        } else {
            return avesRepository.findAll();
        }
    }

    @Override
    public List<Ave> listarAvesPorPlantel(Long plantelId) {
        log.info("[DEBUG] Listando aves do plantel: {}", plantelId);
        return avesRepository.findByPlantelId(plantelId);
    }

    @Override
    public List<Ave> listarAvesPorEspecie(Long especieId) {
        log.info("[DEBUG] Listando aves da espécie: {}", especieId);
        return avesRepository.findByEspecieId(especieId);
    }

    @Override
    public List<Ave> listarAvesPorAnilha(String numeroAnilha) {
        log.info("[DEBUG] Listando aves por anilha: {}", numeroAnilha);
        return avesRepository.findByNumeroAnilhaContaining(numeroAnilha);
    }

    @Override
    public Optional<Ave> obterAvePorId(Long id) {
        log.info("[DEBUG] Buscando ave por id: {}", id);
        return avesRepository.findById(id);
    }

    @Override
    public Ave atualizarAve(Long id, Ave ave) {
        log.info("[DEBUG] Atualizando ave com id: {}", id);
        return avesRepository.findById(id)
                .map(aveExistente -> {
                    aveExistente.setNome(ave.getNome());
                    aveExistente.setIdentificador(ave.getIdentificador());
                    aveExistente.setSexo(ave.getSexo());
                    aveExistente.setCor(ave.getCor());
                    aveExistente.setNumeroAnilha(ave.getNumeroAnilha());
                    aveExistente.setAnilhaTipo(ave.getAnilhaTipo());
                    aveExistente.setAnoAnilha(ave.getAnoAnilha());
                    aveExistente.setStatus(ave.getStatus());
                    aveExistente.setEspecie(ave.getEspecie());
                    aveExistente.setPlantel(ave.getPlantel());
                    aveExistente.setPai(ave.getPai());
                    aveExistente.setMae(ave.getMae());
                    return avesRepository.save(aveExistente);
                })
                .orElseThrow(() -> new RuntimeException("Ave não encontrada com id: " + id));
    }
    
    @Override
    public void deletarAve(Long id) {
        log.info("[DEBUG] Deletando ave com id: {}", id);
        if (!avesRepository.existsById(id)) {
            log.error("[ERROR] Ave não encontrada com id: {}", id);
            throw new RuntimeException("Ave não encontrada com id: " + id);
        }
        avesRepository.deleteById(id);
    }

    @Override
    public List<Ave> listarTodasAves() {
        log.info("[DEBUG] Listando todas as aves");
        return avesRepository.findAll();
    }
}

package com.avidata.api.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.port.in.IEspeciesUseCase;
import com.avidata.api.domain.port.out.IEspeciesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EspeciesService implements IEspeciesUseCase {

  private final IEspeciesRepository especiesRepository;

  @Override
  public Especie criarEspecie(Especie especie) {
    log.info("[DEBUG] Criando especie: {}", especie);
    return especiesRepository.save(especie);
  }

  @Override
  public List<Especie> listarEspecies(String nomePopular) {
    log.info("[DEBUG] Listando especies com nomePopular: {}", nomePopular);
    if (nomePopular != null && !nomePopular.isEmpty()) {
      return especiesRepository.findByNomePopularContaining(nomePopular);
    } else {
      return especiesRepository.findAll();
    }
  }

  @Override
  public Optional<Especie> obterEspeciePorId(Long id) {
    log.info("[DEBUG] Obtendo especie por id: {}", id);
    return especiesRepository.findById(id);
  }

  @Override
  public Especie atualizarEspecie(Long id, Especie especie) {
    log.info("[DEBUG] Atualizando especie com id: {}", id);
    return especiesRepository.findById(id)
        .map(especieExistente -> {
          especieExistente.setClasse(especie.getClasse());
          especieExistente.setFamilia(especie.getFamilia());
          especieExistente.setGenero(especie.getGenero());
          especieExistente.setEspecie(especie.getEspecie());
          especieExistente.setNomePopular(especie.getNomePopular());
          return especiesRepository.save(especieExistente);
        })
        .orElseThrow(() -> new RuntimeException("Especie não encontrada com id: " + id));
  }

  @Override
  public void deletarEspecie(Long id) {
    log.info("[DEBUG] Deletando especie com id: {}", id);
    if (!especiesRepository.existsById(id)) {
      log.error("[ERROR] Especie não encontrada com id: {}", id);
      throw new RuntimeException("Especie não encontrada com id: " + id);
    }
    especiesRepository.deleteById(id);
  }
}

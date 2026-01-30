package com.avidata.api.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.domain.port.in.ITipoAnilhaUseCase;
import com.avidata.api.domain.port.out.ITipoAnilhaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TipoAnilhaService implements ITipoAnilhaUseCase {
  private final ITipoAnilhaRepository tipoAnilhaRepository;

  @Override
  public TipoAnilha criarTipoDeAnilha(TipoAnilha tipoAnilha) {
    log.info("[DEBUG] Criando tipo de anilha: {}", tipoAnilha);
    return tipoAnilhaRepository.save(tipoAnilha);
  }

  @Override
  public TipoAnilha atualizarTipoDeAnilha(Long id, TipoAnilha tipoAnilha) {
    log.info("[DEBUG] Atualizando tipo de anilha com id: {}", id);
    return tipoAnilhaRepository.findById(id)
        .map(existingTipoAnilha -> {
          existingTipoAnilha.setTipoAnilha(tipoAnilha.getTipoAnilha());
          existingTipoAnilha.setFlTipoAnilha(tipoAnilha.getFlTipoAnilha());
          existingTipoAnilha.setDescricao(tipoAnilha.getDescricao());
          return tipoAnilhaRepository.save(existingTipoAnilha);
        })
        .orElseThrow(() -> new RuntimeException("Tipo de anilha não encontrado com id: " + id));
  }

  @Override
  public void deletarTipoDeAnilha(Long id) {
    log.info("[DEBUG] Deletando tipo de anilha com id: {}", id);
    tipoAnilhaRepository.deleteById(id);
  }

  @Override
  public TipoAnilha obterTipoDeAnilhaPorId(Long id) {
    log.info("[DEBUG] Obtendo tipo de anilha por id: {}", id);
    return tipoAnilhaRepository.findById(id)
        .orElse(null);
  }

  @Override
  public List<TipoAnilha> listarTodosTiposDeAnilha() {
    log.info("[DEBUG] Listando todos os tipos de anilha");
    return tipoAnilhaRepository.findAll();
  }
}

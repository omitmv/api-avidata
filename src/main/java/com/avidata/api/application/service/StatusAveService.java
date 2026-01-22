package com.avidata.api.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.domain.port.in.IStatusUseCase;
import com.avidata.api.domain.port.out.IStatusAveRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StatusAveService implements IStatusUseCase {
  private final IStatusAveRepository statusAveRepository;

  @Override
  public StatusAve criarStatusAve(StatusAve statusAve) {
    log.info("[DEBUG] Criando status de ave: {}", statusAve);
    return statusAveRepository.save(statusAve);
  }

  @Override
  public StatusAve atualizarStatusAve(Long id, StatusAve statusAve) {
    log.info("[DEBUG] Atualizando status de ave com id: {}", id);
    return statusAveRepository.findById(id)
            .map(statusExistente -> {
                statusExistente.setStatusAve(statusAve.getStatusAve());
                statusExistente.setFlStatusAve(statusAve.getFlStatusAve());
                statusExistente.setDescricao(statusAve.getDescricao());
                return statusAveRepository.save(statusExistente);
            })
            .orElseThrow(() -> new RuntimeException("Status de ave não encontrado com id: " + id));
  }

  @Override
  public Optional<StatusAve> obterStatusAvePorId(Long id) {
    log.info("[DEBUG] Buscando status de ave por id: {}", id);
    return statusAveRepository.findById(id);
  }

  @Override
  public void deletarStatusAvePorId(Long id) {
    log.info("[DEBUG] Deletando status de ave por id: {}", id);
    statusAveRepository.deleteById(id);
  }

  @Override
  public List<StatusAve> listarTodosStatusAves() {
    log.info("[DEBUG] Listando todos os status de aves");
    return statusAveRepository.findAll();
  }  
}

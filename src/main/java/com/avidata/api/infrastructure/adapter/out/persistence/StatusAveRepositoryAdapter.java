package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.domain.port.out.IStatusAveRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.StatusAveEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.StatusAvePersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatusAveRepositoryAdapter implements IStatusAveRepository {
  private final StatusAveJpaRepository jpaRepository;
  private final StatusAvePersistenceMapper mapper;

  @Override
  public void deleteById(Long id) {
    log.info("[DEBUG] Deletando StatusAve por ID: {}", id);
    jpaRepository.deleteById(id);
  }

  @Override
  public StatusAve save(StatusAve statusAve) {
    log.info("[DEBUG] Salvando StatusAve: {}", statusAve);
    StatusAveEntity entity = mapper.toEntity(statusAve);
    StatusAveEntity savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public StatusAve updateById(Long id, StatusAve statusAve) {
    log.info("[DEBUG] Atualizando StatusAve com ID: {}", id);
    return jpaRepository.findById(id)
        .map(existingEntity -> {
          existingEntity.setFlStatusAve(statusAve.getFlStatusAve());
          existingEntity.setDescricao(statusAve.getDescricao());
          StatusAveEntity updatedEntity = jpaRepository.save(existingEntity);
          return mapper.toDomain(updatedEntity);
        })
        .orElse(null);
  }

  @Override
  public Optional<StatusAve> findById(Long id) {
    log.info("[DEBUG] Buscando StatusAve por ID: {}", id);
    Optional<StatusAveEntity> entity = jpaRepository.findById(id);
    return entity.map(mapper::toDomain);
  }

  @Override
  public List<StatusAve> findAll() {
    log.info("[DEBUG] Buscando todos os StatusAve");
    List<StatusAveEntity> entities = jpaRepository.findAll();
    return entities.stream()
        .map(mapper::toDomain)
        .toList();
  }
}

package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.port.out.IAvesRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.AvesEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.AvesPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AvesRepositoryAdapter implements IAvesRepository {
  private final AvesJpaRepository JpaRepository;
  private final AvesPersistenceMapper mapper;

  @Override
  public Ave save(Ave ave) {
    log.info("[DEBUG] Salvando Ave: {}", ave);
    AvesEntity entity = mapper.toEntity(ave);
    AvesEntity savedEntity = JpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Ave> findById(Long id) {
    log.info("[DEBUG] Buscando Ave por ID: {}", id);
    Optional<AvesEntity> entity = JpaRepository.findById(id);
    return entity.map(mapper::toDomain);
  }

  @Override
  public boolean existsById(Long id) {
    log.info("[DEBUG] Verificando existência de Ave por ID: {}", id);
    return JpaRepository.existsById(id);
  }

  @Override
  public void deleteById(Long id) {
    log.info("[DEBUG] Deletando Ave por ID: {}", id);
    JpaRepository.deleteById(id);
  }

  @Override
  public List<Ave> findAll() {
    log.info("[DEBUG] Buscando todas as Aves");
    List<AvesEntity> entities = JpaRepository.findAll();
    return entities.stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Ave> findByPlantelId(Long plantelId) {
    log.info("[DEBUG] Buscando Aves por Plantel ID: {}", plantelId);
    List<AvesEntity> entities = JpaRepository.findByPlantelId(plantelId);
    return entities.stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Ave> findByNomeContaining(String nome) {
    log.info("[DEBUG] Buscando Aves por nome contendo: {}", nome);
    List<AvesEntity> entities = JpaRepository.findByNomeContaining(nome);
    return entities.stream()
        .map(mapper::toDomain)
        .toList();
  }
}

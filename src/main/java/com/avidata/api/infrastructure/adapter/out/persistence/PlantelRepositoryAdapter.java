package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.domain.port.out.IPlantelRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.PlantelPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlantelRepositoryAdapter implements IPlantelRepository {
  private final PlantelJpaRepository jpaRepository;
  private final PlantelPersistenceMapper mapper;

  @Override
  public Plantel save(Plantel plantel) {
    log.info("[DEBUG] Salvando Plantel: {}", plantel);
    var entity = mapper.toEntity(plantel);
    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Plantel> findById(Long id) {
    log.info("[DEBUG] Buscando Plantel por ID: {}", id);
    var entity = jpaRepository.findById(id);
    return entity.map(mapper::toDomain);
  }

  @Override
  public boolean existsById(Long id) {
    log.info("[DEBUG] Verificando existência de Plantel por ID: {}", id);
    return jpaRepository.existsById(id);
  }

  @Override
  public void deleteById(Long id) {
    log.info("[DEBUG] Deletando Plantel por ID: {}", id);
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Plantel> findAll() {
    log.info("[DEBUG] Buscando todos os Plantels");
    var entities = jpaRepository.findAll();
    return entities.stream()
                   .map(mapper::toDomain)
                   .toList();
  }

  @Override
  public List<Plantel> findByNomeContaining(String nome) {
    log.info("[DEBUG] Buscando Plantels contendo nome: {}", nome);
    var entities = jpaRepository.findByNomeContaining(nome);
    return entities.stream()
                   .map(mapper::toDomain)
                   .toList();
  }
}

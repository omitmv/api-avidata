package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.port.out.IEspeciesRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.EspeciesPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EspeciesRepositoryAdapter implements IEspeciesRepository {
  private final EspeciesJpaRepository jpaRepository;
  private final EspeciesPersistenceMapper mapper;

  @Override
  public Especie save(Especie especie) {
    log.info("[DEBUG] Salvando Especie: {}", especie);
    var entity = mapper.toEntity(especie);
    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Especie> findById(Long id) {
    log.info("[DEBUG] Buscando Especie por ID: {}", id);
    var entity = jpaRepository.findById(id);
    return entity.map(mapper::toDomain);
  }

  @Override
  public boolean existsById(Long id) {
    log.info("[DEBUG] Verificando existência de Especie por ID: {}", id);
    return jpaRepository.existsById(id);
  }

  @Override
  public void deleteById(Long id) {
    log.info("[DEBUG] Deletando Especie por ID: {}", id);
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Especie> findAll() {
    log.info("[DEBUG] Buscando todas as Especies");
    var entities = jpaRepository.findAll();
    return entities.stream()
                   .map(mapper::toDomain)
                   .toList();
  }

  @Override
  public List<Especie> findByNomePopularContaining(String nomePopular) {
    log.info("[DEBUG] Buscando Especies por nome popular contendo: {}", nomePopular);
    var entities = jpaRepository.findByNomePopularContaining(nomePopular);
    return entities.stream()
                   .map(mapper::toDomain)
                   .toList();
  }
}

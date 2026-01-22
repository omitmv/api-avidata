package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.domain.port.out.ITipoAnilhaRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.TipoAnilhaPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TipoAnilhaRepositoryAdapter implements ITipoAnilhaRepository {
  private final TipoAnilhaJpaRepository jpaRepository;
  private final TipoAnilhaPersistenceMapper mapper;

  @Override
  public TipoAnilha save(TipoAnilha tipoAnilha) {
    log.info("[DEBUG] Salvando TipoAnilha: {}", tipoAnilha);
    var entity = mapper.toEntity(tipoAnilha);
    var savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<TipoAnilha> findById(Long id) {
    log.info("[DEBUG] Buscando TipoAnilha por ID: {}", id);
    var entity = jpaRepository.findById(id);
    return entity.map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id) {
    log.info("[DEBUG] Deletando TipoAnilha por ID: {}", id);
    jpaRepository.deleteById(id);
  }

  @Override
  public TipoAnilha updateById(Long id, TipoAnilha tipoAnilha) {
    log.info("[DEBUG] Atualizando TipoAnilha com ID: {}", id);
    return jpaRepository.findById(id)
        .map(existingEntity -> {
          existingEntity.setFlTipoAnilha(tipoAnilha.getFlTipoAnilha());
          existingEntity.setTipoAnilha(tipoAnilha.getTipoAnilha());
          existingEntity.setDescricao(tipoAnilha.getDescricao());
          var updatedEntity = jpaRepository.save(existingEntity);
          return mapper.toDomain(updatedEntity);
        })
        .orElse(null);
  }

  @Override
  public List<TipoAnilha> findAll() {
    log.info("[DEBUG] Buscando todos os TipoAnilha");
    var entities = jpaRepository.findAll();
    return entities.stream()
        .map(mapper::toDomain)
        .toList();
  }
}

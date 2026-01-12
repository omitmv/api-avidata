package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.domain.port.out.IUsuarioRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepositoryAdapter implements IUsuarioRepository {
  
  private final UserJpaRepository jpaRepository;
  private final UsuarioPersistenceMapper mapper;

  @Override
  public Usuario save(Usuario usuario) {
    log.info("[DEBUG] Criando usuario: {}", usuario);
    UserEntity entity = mapper.toEntity(usuario);
    UserEntity savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Usuario> findByUsername(String username) {
    log.info("[DEBUG] Encontrando usuario por username: {}", username);
    return jpaRepository.findByUsername(username)
      .map(mapper::toDomain);
  }

  @Override
  public List<Usuario> findAll() {
    log.info("[DEBUG] Encontrando todos os usuarios");
    return jpaRepository.findAll().stream()
      .map(mapper::toDomain)
      .toList();
  }

  @Override
  public void deleteByUsername(String username) {
    log.info("[DEBUG] Deletando usuario por username: {}", username);
    jpaRepository.deleteByUsername(username);
  }

  @Override
  public Usuario update(String username, Usuario usuario) {
    log.info("[DEBUG] Atualizando usuario por username: {} e dados: {}", username, usuario);
    UserEntity entity = mapper.toEntity(usuario);
    UserEntity updatedEntity = jpaRepository.save(entity);
    return mapper.toDomain(updatedEntity);
  }

  @Override
  public boolean existsByUsername(String username) {
    log.info("[DEBUG] Verificando existência de usuario por username: {}", username);
    return jpaRepository.existsByUsername(username);
  }

  @Override
  public List<Usuario> findByUsernameContaining(String username) {
    log.info("[DEBUG] Encontrando usuarios por username contendo: {}", username);
    List<UserEntity> entities = jpaRepository.findByUsernameContainingAndEnabledTrue(username);
    return entities.stream()
      .map(mapper::toDomain)
      .toList();
  }
}

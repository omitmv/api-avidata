package com.avidata.api.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.domain.port.in.IUsuarioUseCase;
import com.avidata.api.domain.port.out.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsuarioService implements IUsuarioUseCase {
  
  private final IUsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Usuario salvarUsuario(Usuario usuario) {
    usuario.setCreatedAt(LocalDateTime.now());
    usuario.setUpdatedAt(LocalDateTime.now());
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    log.info("[DEBUG] Salvando usuario: {}", usuario);
    return usuarioRepository.save(usuario);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> buscarPorUsername(String username) {
    log.info("[DEBUG] Encontrando usuario por username: {}", username);
    return usuarioRepository.findByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> listarUsuarios(String username) {
    log.info("[DEBUG] Listar todos os usuarios com filtro de username: {}", username);
    return usuarioRepository.findByUsernameContaining(username);
  }

  @Override
  public Usuario atualizarUsuario(String username, Usuario usuario) {
    log.info("[DEBUG] Atualizando usuario com username: {} e dados: {}", username, usuario);
    return usuarioRepository.findByUsername(username)
            .map(existingUsuario -> {
                existingUsuario.setEmail(usuario.getEmail());
                existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                existingUsuario.setUpdatedAt(LocalDateTime.now());
                existingUsuario.setEnabled(usuario.getEnabled());
                existingUsuario.setRoles(usuario.getRoles());
                return usuarioRepository.update(username, existingUsuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com username: " + username));
  }

  @Override
  public void deletarUsuario(String username) {
    log.info("[DEBUG] Deletando usuario com username: {}", username);
    if (!usuarioRepository.existsByUsername(username)) {
        log.error("[ERROR] Usuario não encontrado com username: {}", username);
        throw new RuntimeException("Usuário não encontrado com username: " + username);
    }
    usuarioRepository.deleteByUsername(username);
  }
}

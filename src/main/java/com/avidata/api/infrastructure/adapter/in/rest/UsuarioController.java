package com.avidata.api.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.application.service.UsuarioService;
import com.avidata.api.domain.model.Usuario;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioResponse;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioValidacaoRequest;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.UsuarioRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.UsuarioSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController implements UsuarioSwagger {

  private final UsuarioService usuarioService;

  @Override
  @PostMapping
  public ResponseEntity<Void> createUsuario(@Valid @RequestBody UsuarioRequest request) {
    log.info("[DEBUG] Criando usuario com dados: {}", request);
    usuarioService.salvarUsuario(new UsuarioRestMapper().toDomain(request));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  @PutMapping("/{login}")
  public ResponseEntity<Void> updateUsuario(
    @PathVariable String login,
    @Valid @RequestBody UsuarioRequest request
  ) {
    log.info("[DEBUG] Atualizando usuario com login: {} e dados: {}", login, request);
    return usuarioService.buscarPorUsername(login)
      .map(existingUsuario -> {
        Usuario updatedUsuario = new UsuarioRestMapper().toDomain(request);
        usuarioService.atualizarUsuario(login, updatedUsuario);
        return ResponseEntity.ok().<Void>build();
      })
      .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @DeleteMapping("/{login}")
  public ResponseEntity<Void> deleteUsuario(
    @PathVariable String login
  ) {
    log.info("[DEBUG] Deletando usuario com login: {}", login);
    return usuarioService.buscarPorUsername(login)
      .map(existingUsuario -> {
        usuarioService.deletarUsuario(login);
        return ResponseEntity.ok().<Void>build();
      })
      .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @GetMapping("/{login}")
  public ResponseEntity<List<Usuario>> getActiveUsuariosByLogin(
    @PathVariable String login
  ) {
    log.info("[DEBUG] Obtendo usuarios ativos com login: {}", login);
    List<Usuario> usuarios = usuarioService.listarUsuarios(login);
    return ResponseEntity.ok(usuarios);
  }

  @Override
  @GetMapping("/detail/{login}")
  public ResponseEntity<UsuarioResponse> getUsuarioByLogin(
    @PathVariable String login
  ) {
    log.info("[DEBUG] Obtendo usuario com login: {}", login);
    return usuarioService.buscarPorUsername(login)
      .map(usuario -> ResponseEntity.ok(new UsuarioRestMapper().toResponse(usuario)))
      .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @PostMapping("/validate")
  public ResponseEntity<Boolean> validateUsuario(@Valid @RequestBody UsuarioValidacaoRequest request) {
    log.info("[DEBUG] Validando usuario com dados: {}", request);
    return ResponseEntity.ok(usuarioService.validarUsernameAndPassword(request.getUsername(), request.getPassword()));
  }
}

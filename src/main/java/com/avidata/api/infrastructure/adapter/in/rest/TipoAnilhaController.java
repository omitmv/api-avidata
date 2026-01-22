package com.avidata.api.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.domain.port.in.ITipoAnilhaUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.TipoAnilhaRequest;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.TipoAnilhaRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.TipoAnilhaSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tipos-anilha")
@RequiredArgsConstructor
@Slf4j
public class TipoAnilhaController implements TipoAnilhaSwagger {
  private final ITipoAnilhaUseCase tipoAnilhaUseCase;
  private final TipoAnilhaRestMapper mapper;

  @Override
  @GetMapping
  public ResponseEntity<List<TipoAnilha>> listarTodosOsTiposDeAnilha() {
    log.info("[DEBUG] Listando todos os tipos de anilha");
    List<TipoAnilha> tiposAnilha = tipoAnilhaUseCase.listarTodosTiposDeAnilha();
    return ResponseEntity.ok(tiposAnilha);
  }

  @Override
  @GetMapping("/id/{id}")
  public ResponseEntity<TipoAnilha> obterTipoDeAnilhaPorId(@PathVariable Long id) {
    log.info("[DEBUG] Obtendo tipo de anilha por id: {}", id);
    TipoAnilha tipoAnilha = tipoAnilhaUseCase.obterTipoDeAnilhaPorId(id);
    if (tipoAnilha != null) {
      return ResponseEntity.ok(tipoAnilha);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  @PostMapping
  public ResponseEntity<TipoAnilha> criarTipoDeAnilha(@Valid @RequestBody TipoAnilhaRequest request) {
    log.info("[DEBUG] Criando tipo de anilha com dados: {}", request);
    TipoAnilha tipoAnilha = mapper.toDomain(request);
    TipoAnilha criada = tipoAnilhaUseCase.criarTipoDeAnilha(tipoAnilha);
    return ResponseEntity.status(201).body(criada);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<TipoAnilha> atualizarTipoDeAnilha(@PathVariable Long id, @Valid @RequestBody TipoAnilhaRequest request) {
    log.info("[DEBUG] Atualizando tipo de anilha com ID: {} e dados: {}", id, request);
    TipoAnilha tipoAnilha = mapper.toDomain(request);
    TipoAnilha atualizada = tipoAnilhaUseCase.atualizarTipoDeAnilha(id, tipoAnilha);
    if (atualizada != null) {
      return ResponseEntity.ok(atualizada);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarTipoDeAnilha(@PathVariable Long id) {
    log.info("[DEBUG] Deletando tipo de anilha com ID: {}", id);
    tipoAnilhaUseCase.deletarTipoDeAnilha(id);
    return ResponseEntity.noContent().build();
  }
}

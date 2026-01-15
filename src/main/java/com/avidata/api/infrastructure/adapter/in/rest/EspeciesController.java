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

import com.avidata.api.domain.model.Especie;
import com.avidata.api.domain.port.in.IEspeciesUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspecieRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspeciesResponse;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.EspeciesRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.EspeciesSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/especies")
@RequiredArgsConstructor
@Slf4j
public class EspeciesController implements EspeciesSwagger {
  private final IEspeciesUseCase especiesUseCase;
  private final EspeciesRestMapper mapper;

  @Override
  @PostMapping
  public ResponseEntity<EspeciesResponse> criarEspecie(@Valid @RequestBody EspecieRequest request) {
    log.info("Criando espécie: {}", request);
    Especie especie = mapper.toDomain(request);
    Especie criada = especiesUseCase.criarEspecie(especie);
    EspeciesResponse response = mapper.toResponse(criada);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  @GetMapping("/nomePopular/{nomePopular}")
  public ResponseEntity<List<Especie>> listarEspeciesPorNomePopular(@PathVariable String nomePopular) {
    return ResponseEntity.ok(especiesUseCase.listarEspecies(nomePopular));
  }

  @Override
  @GetMapping
  public ResponseEntity<List<Especie>> listarTodasEspecies() {
    log.info("[DEBUG] Listando todas as espécies");
    return ResponseEntity.ok(especiesUseCase.listarTodos());
  }

  @Override
  @GetMapping("/id/{id}")
  public ResponseEntity<Especie> obterEspeciePorId(@PathVariable Long id) {
    log.info("[DEBUG] Obtendo espécie por id: {}", id);
    return especiesUseCase.obterEspeciePorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<Especie> atualizarEspecie(@PathVariable Long id, @Valid @RequestBody EspecieRequest request) {
    log.info("[DEBUG] Atualizando especie com id: {} e dados: {}", id, request);
    Especie especie = mapper.toDomain(request);
    Especie especieAtualizada = especiesUseCase.atualizarEspecie(id, especie);
    return ResponseEntity.ok(especieAtualizada);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarEspecie(@PathVariable Long id) {
    log.info("[DEBUG] Deletando especie com id: {}", id);
    especiesUseCase.deletarEspecie(id);
    return ResponseEntity.noContent().build();
  }  
}

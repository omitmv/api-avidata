package com.avidata.api.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.domain.port.in.IPlantelUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelResponse;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.PlantelRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.PlantelSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/plantel")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(
    origins = {"https://www.avidata.com.br", "http://localhost:3000", "https://black-tree-04b4acb0f.1.azurestaticapps.net"},
    allowCredentials = "true"
)
public class PlantelController implements PlantelSwagger {
  private final IPlantelUseCase plantelUseCase;
  private final PlantelRestMapper mapper;
  
  @Override
  @PostMapping
  public ResponseEntity<PlantelResponse> criarPlantel(@Valid @RequestBody PlantelRequest request) {
    log.info("[DEBUG] Criando um novo plantel com os dados: {}", request);
    Plantel plantel = mapper.toDomain(request);
    Plantel novoPlantel = plantelUseCase.criarPlantel(plantel);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(novoPlantel));
  }

  @Override
  @GetMapping("/nome/{nome}")
  public ResponseEntity<List<Plantel>> listarPlantelPorNome(@PathVariable String nome) {
    log.info("[DEBUG] Listando plantel por nome: {}", nome);
    return ResponseEntity.ok(plantelUseCase.listarPlantels(nome));
  }

  @Override
  @GetMapping("/id/{id}")
  public ResponseEntity<Plantel> obterPlantelPorId(@PathVariable Long id) {
    log.info("[DEBUG] Obtendo plantel por id: {}", id);
    return plantelUseCase.obterPlantelPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<Plantel> atualizarPlantel(@PathVariable Long id, @Valid @RequestBody PlantelRequest request) {
    log.info("[DEBUG] Atualizando plantel com id: {} e dados: {}", id, request);
    Plantel plantel = mapper.toDomain(request);
    Plantel plantelAtualizado = plantelUseCase.atualizarPlantel(id, plantel);
    return ResponseEntity.ok(plantelAtualizado);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarPlantel(@PathVariable Long id) {
    log.info("[DEBUG] Deletando plantel com id: {}", id);
    plantelUseCase.deletarPlantel(id);
    return ResponseEntity.noContent().build();
  }
}

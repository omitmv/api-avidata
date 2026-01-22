package com.avidata.api.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.domain.port.in.IStatusUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.StatusAveRequest;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.StatusAveRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.StatusAveSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/status-aves")
@RequiredArgsConstructor
@Slf4j
public class StatusAveController implements StatusAveSwagger {
  private final IStatusUseCase statusUseCase;
  private final StatusAveRestMapper mapper;

  @Override
  @PostMapping
  public ResponseEntity<StatusAve> criarStatusAve(@Valid @RequestBody StatusAveRequest request) {
    log.info("[DEBUG] Criando um novo StatusAve com os dados: {}", request);
    StatusAve statusAve = mapper.toDomain(request);
    StatusAve novoStatusAve = statusUseCase.criarStatusAve(statusAve);
    return ResponseEntity.status(201).body(novoStatusAve);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<StatusAve>> listarTodosStatusAves() {
    log.info("[DEBUG] Listando todos os StatusAves");
    List<StatusAve> statusAves = statusUseCase.listarTodosStatusAves();
    return ResponseEntity.ok(statusAves);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<StatusAve> obterStatusAvePorId(@PathVariable Long id) {
    log.info("[DEBUG] Obtendo StatusAve por id: {}", id);
    Optional<StatusAve> statusAve = statusUseCase.obterStatusAvePorId(id);
    if (statusAve.isPresent()) {
      return ResponseEntity.ok(statusAve.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<StatusAve> atualizarStatusAvePorId(@PathVariable Long id, @Valid @RequestBody StatusAveRequest request) {
    log.info("[DEBUG] Atualizando StatusAve com id: {} e dados: {}", id, request);
    StatusAve statusAve = mapper.toDomain(request);
    StatusAve atualizado = statusUseCase.atualizarStatusAve(id, statusAve);
    if (atualizado != null) {
      return ResponseEntity.ok(atualizado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarStatusAvePorId(@PathVariable Long id) {
    log.info("[DEBUG] Deletando StatusAve com id: {}", id);
    statusUseCase.deletarStatusAvePorId(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping("/status/{statusAve}")
  public ResponseEntity<List<StatusAve>> listarTodosStatusAvesByStatusAve(@PathVariable String statusAve) {
    log.info("[DEBUG] Listando StatusAves por status: {}", statusAve);
    List<StatusAve> statusAves = statusUseCase.listarTodosStatusAves();
    List<StatusAve> filteredStatusAves = statusAves.stream()
        .filter(sa -> sa.getFlStatusAve().equalsIgnoreCase(statusAve))
        .toList();
    return ResponseEntity.ok(filteredStatusAves);
  }
}

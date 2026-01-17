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

import com.avidata.api.domain.model.Ave;
import com.avidata.api.domain.port.in.IAveUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AveRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AveResponse;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.AveRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.AveSwagger;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/aves")
@RequiredArgsConstructor
@Slf4j
public class AveController implements AveSwagger {
    private final IAveUseCase aveUseCase;
    private final AveRestMapper mapper;
    
    @Override
    @PostMapping
    public ResponseEntity<AveResponse> criarAve(@Valid @RequestBody AveRequest request) {
        log.info("[DEBUG] Criando uma nova ave com os dados: {}", request);
        Ave ave = mapper.toDomain(request);
        Ave novaAve = aveUseCase.criarAve(ave);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(novaAve));
    }

    @Override
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AveResponse>> listarAvesPorNome(@PathVariable String nome) {
        log.info("[DEBUG] Listando aves por nome: {}", nome);
        List<Ave> aves = aveUseCase.listarAves(nome);
        return ResponseEntity.ok(mapper.toResponseList(aves));
    }

    @Override
    @GetMapping("/plantel/{plantelId}")
    public ResponseEntity<List<AveResponse>> listarAvesPorPlantel(@PathVariable Long plantelId) {
        log.info("[DEBUG] Listando aves do plantel: {}", plantelId);
        List<Ave> aves = aveUseCase.listarAvesPorPlantel(plantelId);
        return ResponseEntity.ok(mapper.toResponseList(aves));
    }

    @Override
    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<AveResponse>> listarAvesPorEspecie(@PathVariable Long especieId) {
        log.info("[DEBUG] Listando aves da espécie: {}", especieId);
        List<Ave> aves = aveUseCase.listarAvesPorEspecie(especieId);
        return ResponseEntity.ok(mapper.toResponseList(aves));
    }

    @Override
    @GetMapping("/anilha/{numeroAnilha}")
    public ResponseEntity<List<AveResponse>> listarAvesPorAnilha(@PathVariable String numeroAnilha) {
        log.info("[DEBUG] Listando aves por anilha: {}", numeroAnilha);
        List<Ave> aves = aveUseCase.listarAvesPorAnilha(numeroAnilha);
        return ResponseEntity.ok(mapper.toResponseList(aves));
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<AveResponse> obterAvePorId(@PathVariable Long id) {
        log.info("[DEBUG] Obtendo ave por id: {}", id);
        return aveUseCase.obterAvePorId(id)
                .map(ave -> ResponseEntity.ok(mapper.toResponse(ave)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AveResponse> atualizarAve(@PathVariable Long id, @Valid @RequestBody AveRequest request) {
        log.info("[DEBUG] Atualizando ave com id: {} e dados: {}", id, request);
        Ave ave = mapper.toDomain(request);
        Ave aveAtualizada = aveUseCase.atualizarAve(id, ave);
        return ResponseEntity.ok(mapper.toResponse(aveAtualizada));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAve(@PathVariable Long id) {
        log.info("[DEBUG] Deletando ave com id: {}", id);
        aveUseCase.deletarAve(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AveResponse>> listarTodasAves() {
        log.info("[DEBUG] Listando todas as aves");
        List<Ave> aves = aveUseCase.listarTodasAves();
        return ResponseEntity.ok(mapper.toResponseList(aves));
    }
}

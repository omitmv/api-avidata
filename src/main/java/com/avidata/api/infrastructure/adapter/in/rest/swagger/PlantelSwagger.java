package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.avidata.api.domain.model.Plantel;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.PlantelResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Plantels", description = "API para gerenciamento de plantéis")
public interface PlantelSwagger {
	@Operation(summary = "Criar novo plantel", description = "Cria um novo plantel no sistema com as informações fornecidas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Plantel criado com sucesso", content = @Content(schema = @Schema(implementation = PlantelResponse.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
	})
	ResponseEntity<PlantelResponse> criarPlantel(
			@RequestBody(description = "Dados do plantel a ser criado", required = true) @Valid @org.springframework.web.bind.annotation.RequestBody PlantelRequest request);

	@Operation(summary = "Listar plantel por nome", description = "Recupera lista de plantéis existentes pelo seu nome")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plantel encontrado", content = @Content(schema = @Schema(implementation = PlantelResponse.class))),
			@ApiResponse(responseCode = "404", description = "Plantel não encontrado")
	})
	ResponseEntity<List<Plantel>> listarPlantelPorNome(String nome);

	@Operation(summary = "Obter plantel por ID", description = "Recupera um plantel existente pelo seu ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plantel encontrado", content = @Content(schema = @Schema(implementation = PlantelResponse.class))),
			@ApiResponse(responseCode = "404", description = "Plantel não encontrado")
	})
	ResponseEntity<Plantel> obterPlantelPorId(Long id);

	@Operation(summary = "Atualizar plantel", description = "Atualiza as informações de um plantel existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plantel atualizado com sucesso", content = @Content(schema = @Schema(implementation = PlantelResponse.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
			@ApiResponse(responseCode = "404", description = "Plantel não encontrado")
	})
	ResponseEntity<Plantel> atualizarPlantel(
			Long id,
			@RequestBody(description = "Dados do plantel a ser atualizado", required = true) @Valid @org.springframework.web.bind.annotation.RequestBody PlantelRequest request);

	@Operation(summary = "Deletar plantel", description = "Remove um plantel existente do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Plantel deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Plantel não encontrado")
	})
	ResponseEntity<Void> deletarPlantel(Long id);

	@Operation(summary = "Listar todos os plantéis", description = "Recupera uma lista de todos os plantéis existentes no sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de plantéis recuperada com sucesso", content = @Content(schema = @Schema(implementation = PlantelResponse.class))),
			@ApiResponse(responseCode = "404", description = "Nenhum plantel encontrado")
	})
	ResponseEntity<List<Plantel>> listarTodosPlantels();
}

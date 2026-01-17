package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.avidata.api.infrastructure.adapter.in.rest.dto.AveRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AveResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Aves", description = "Endpoints para gerenciamento de aves")
public interface AveSwagger {

    @Operation(summary = "Criar uma nova ave", description = "Cria uma nova ave no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ave criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<AveResponse> criarAve(
        @Parameter(description = "Dados da ave a ser criada", required = true)
        @Valid @RequestBody AveRequest request
    );

    @Operation(summary = "Listar aves por nome", description = "Lista todas as aves cujo nome contém o texto fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de aves retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<AveResponse>> listarAvesPorNome(
        @Parameter(description = "Nome ou parte do nome da ave", required = true)
        @PathVariable String nome
    );

    @Operation(summary = "Listar aves por plantel", description = "Lista todas as aves de um plantel específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de aves retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<AveResponse>> listarAvesPorPlantel(
        @Parameter(description = "ID do plantel", required = true)
        @PathVariable Long plantelId
    );

    @Operation(summary = "Listar aves por espécie", description = "Lista todas as aves de uma espécie específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de aves retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<AveResponse>> listarAvesPorEspecie(
        @Parameter(description = "ID da espécie", required = true)
        @PathVariable Long especieId
    );

    @Operation(summary = "Listar aves por anilha", description = "Lista todas as aves cujo número de anilha contém o texto fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de aves retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<AveResponse>> listarAvesPorAnilha(
        @Parameter(description = "Número ou parte do número da anilha", required = true)
        @PathVariable String numeroAnilha
    );

    @Operation(summary = "Obter ave por ID", description = "Retorna os detalhes de uma ave específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ave encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ave não encontrada"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<AveResponse> obterAvePorId(
        @Parameter(description = "ID da ave", required = true)
        @PathVariable Long id
    );

    @Operation(summary = "Atualizar ave", description = "Atualiza os dados de uma ave existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ave atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ave não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<AveResponse> atualizarAve(
        @Parameter(description = "ID da ave", required = true)
        @PathVariable Long id,
        @Parameter(description = "Novos dados da ave", required = true)
        @Valid @RequestBody AveRequest request
    );

    @Operation(summary = "Deletar ave", description = "Remove uma ave do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ave deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ave não encontrada"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<Void> deletarAve(
        @Parameter(description = "ID da ave", required = true)
        @PathVariable Long id
    );

    @Operation(summary = "Listar todas as aves", description = "Retorna a lista completa de todas as aves cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de aves retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    ResponseEntity<List<AveResponse>> listarTodasAves();
}

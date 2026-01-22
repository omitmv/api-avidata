package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.avidata.api.domain.model.StatusAve;
import com.avidata.api.infrastructure.adapter.in.rest.dto.StatusAveRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "StatusAves", description = "Endpoints para gerenciamento de status de aves")
public interface StatusAveSwagger {
  @Operation(summary = "Criar um novo status de ave", description = "Cria um novo status de ave no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Status de ave criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<StatusAve> criarStatusAve(
      @Parameter(description = "Dados do status de ave a ser criado", required = true)
      @Valid @RequestBody StatusAveRequest request
  );

  @Operation(summary = "Listar todos os status de aves", description = "Lista todos os status de aves no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de status de aves retornada com sucesso"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<List<StatusAve>> listarTodosStatusAves();

  @Operation(summary = "Obter status de ave por ID", description = "Obtém um status de ave específico pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Status de ave retornado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Status de ave não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<StatusAve> obterStatusAvePorId(Long id);

  @Operation(summary = "Atualizar status de ave por ID", description = "Atualiza um status de ave específico pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Status de ave atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Status de ave não encontrado"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<StatusAve> atualizarStatusAvePorId(
      @Parameter(description = "ID do status de ave a ser atualizado", required = true)
      Long id,
      @Parameter(description = "Dados atualizados do status de ave", required = true)
      @Valid @RequestBody StatusAveRequest request
  );

  @Operation(summary = "Deletar status de ave por ID", description = "Deleta um status de ave específico pelo seu ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Status de ave deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Status de ave não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<Void> deletarStatusAvePorId(
      @Parameter(description = "ID do status de ave a ser deletado", required = true)
      Long id
  );

  @Operation(summary = "Listar todos os status de aves que contenha no nome", description = "Lista todos os status de aves no sistema que contenha no nome")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de status de aves retornada com sucesso"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<List<StatusAve>> listarTodosStatusAvesByStatusAve(
    @Parameter(description = "Parte do nome do status de ave para filtro", required = true) String statusAve
  );
}

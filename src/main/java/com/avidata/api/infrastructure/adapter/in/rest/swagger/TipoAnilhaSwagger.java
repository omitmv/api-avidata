package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.avidata.api.domain.model.TipoAnilha;
import com.avidata.api.infrastructure.adapter.in.rest.dto.TipoAnilhaRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tipos de Anilha", description = "Endpoints para gerenciamento de tipos de anilha")
public interface TipoAnilhaSwagger {
  @Operation(summary = "Listar todos os tipos de anilha", description = "Retorna uma lista de todos os tipos de anilha disponíveis no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de tipos de anilha retornada com sucesso"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<List<TipoAnilha>> listarTodosOsTiposDeAnilha();

  @Operation(summary = "Obter tipo de anilha por ID", description = "Retorna os detalhes de um tipo de anilha específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Tipo de anilha retornado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Tipo de anilha não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<TipoAnilha> obterTipoDeAnilhaPorId(@PathVariable Long id);

  @Operation(summary = "Criar um novo tipo de anilha", description = "Cria um novo tipo de anilha no sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Tipo de anilha criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<TipoAnilha> criarTipoDeAnilha(
    @Parameter(description = "Tipo de anilha a ser criado")
    @Valid @RequestBody TipoAnilhaRequest request);

  @Operation(summary = "Atualizar um tipo de anilha existente", description = "Atualiza os detalhes de um tipo de anilha existente com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Tipo de anilha atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Tipo de anilha não encontrado"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<TipoAnilha> atualizarTipoDeAnilha(@PathVariable Long id, @Valid @RequestBody TipoAnilhaRequest request);

  @Operation(summary = "Deletar um tipo de anilha", description = "Remove um tipo de anilha do sistema com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Tipo de anilha deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Tipo de anilha não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autorizado")
  })
  ResponseEntity<Void> deletarTipoDeAnilha(@PathVariable Long id);
}

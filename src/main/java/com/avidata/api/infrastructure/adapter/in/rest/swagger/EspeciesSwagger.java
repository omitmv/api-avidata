package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.avidata.api.domain.model.Especie;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspecieRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.EspeciesResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Especies", description = "API para gerenciamento de espécies")
public interface EspeciesSwagger {
  @Operation(
      summary = "Criar nova espécie",
      description = "Cria uma nova espécie no sistema com as informações fornecidas"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Espécie criada com sucesso"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Dados inválidos fornecidos"
      )
  })
  ResponseEntity<EspeciesResponse> criarEspecie(
    @RequestBody(
      description = "Dados da espécie a ser criada",
      required = true
    )
    @Valid @org.springframework.web.bind.annotation.RequestBody EspecieRequest request
  );

  @Operation(
      summary = "Listar espécies por nome popular",
      description = "Recupera lista de espécies existentes pelo seu nome popular"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Espécies encontradas"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Espécies não encontradas"
      )
  })
  ResponseEntity<List<Especie>> listarEspeciesPorNomePopular(@PathVariable String nomePopular);

	@Operation(
		summary = "Listar todas as espécies",
		description = "Recupera lista de todas as espécies existentes no sistema"
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Espécies encontradas"
		)
	})
	ResponseEntity<List<Especie>> listarTodasEspecies();

	@Operation(
      summary = "Obter espécie por ID",
      description = "Recupera uma espécie existente pelo seu ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Espécie encontrada"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Espécie não encontrada"
      )
  })
  ResponseEntity<Especie> obterEspeciePorId(@PathVariable Long id);

  @Operation(
      summary = "Atualizar espécie",
      description = "Atualiza uma espécie existente com as novas informações fornecidas"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Espécie atualizada com sucesso"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Espécie não encontrada"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Dados inválidos fornecidos"
      )
  })
  ResponseEntity<Especie> atualizarEspecie(
    @PathVariable Long id,
    @RequestBody(
      description = "Dados da espécie a ser atualizada",
      required = true
    )
    @Valid @org.springframework.web.bind.annotation.RequestBody EspecieRequest request
  );

  @Operation(
      summary = "Deletar espécie",
      description = "Remove uma espécie existente do sistema pelo seu ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204",
          description = "Espécie deletada com sucesso"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Espécie não encontrada"
      )
  })
  ResponseEntity<Void> deletarEspecie(@PathVariable Long id);
}

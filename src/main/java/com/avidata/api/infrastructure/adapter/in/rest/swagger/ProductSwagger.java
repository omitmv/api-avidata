package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Products", description = "API para gerenciamento de produtos")
public interface ProductSwagger {
    @Operation(
            summary = "Criar novo produto",
            description = "Cria um novo produto no sistema com as informações fornecidas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos"
            )
    })
    ResponseEntity<ProductResponse> createProduct(
      @RequestBody(
        description = "Dados do produto a ser criado",
        required = true
      )
      @Valid @org.springframework.web.bind.annotation.RequestBody ProductRequest request
    );
    
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto específico pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    ResponseEntity<ProductResponse> getProduct(
      @Parameter(description = "ID do produto", required = true)
      @PathVariable Long id
    );

    @Operation(
            summary = "Listar todos os produtos",
            description = "Retorna uma lista com todos os produtos cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos retornada com sucesso"
            )
    })
    ResponseEntity<List<ProductResponse>> getAllProducts();

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza as informações de um produto existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    ResponseEntity<ProductResponse> updateProduct(
      @Parameter(description = "ID do produto", required = true)
      @PathVariable Long id,
      @RequestBody(
        description = "Novos dados do produto",
        required = true
      )
      @Valid @org.springframework.web.bind.annotation.RequestBody ProductRequest request);

    @Operation(
            summary = "Deletar produto",
            description = "Remove um produto do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    ResponseEntity<Void> deleteProduct(
      @Parameter(description = "ID do produto", required = true)
      @PathVariable Long id);

    @Operation(
            summary = "Atualizar estoque",
            description = "Atualiza a quantidade em estoque de um produto. Use valores positivos para adicionar e negativos para remover"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quantidade inválida ou estoque insuficiente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    ResponseEntity<ProductResponse> updateStock(
      @Parameter(description = "ID do produto", required = true)
      @PathVariable Long id,
      @Parameter(
        description = "Quantidade a adicionar (positivo) ou remover (negativo) do estoque",
        required = true,
        example = "10"
      )
      @RequestParam Integer quantity);
}

package com.avidata.api.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Product responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo os dados de um produto")
public class ProductResponse {
    
    @Schema(description = "ID único do produto", example = "1")
    private Long id;
    
    @Schema(description = "Nome do produto", example = "Notebook Dell Inspiron 15")
    private String name;
    
    @Schema(description = "Descrição do produto", example = "Notebook Dell com processador Intel i7")
    private String description;
    
    @Schema(description = "Preço do produto", example = "3500.00")
    private BigDecimal price;
    
    @Schema(description = "Quantidade em estoque", example = "10")
    private Integer stock;
    
    @Schema(description = "Indica se o produto está disponível (estoque > 0)", example = "true")
    private boolean available;
    
    @Schema(description = "Data e hora de criação do produto", example = "2026-01-07T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Data e hora da última atualização", example = "2026-01-07T10:30:00")
    private LocalDateTime updatedAt;
}

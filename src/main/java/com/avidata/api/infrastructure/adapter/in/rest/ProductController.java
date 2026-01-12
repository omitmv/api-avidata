package com.avidata.api.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.avidata.api.domain.model.Product;
import com.avidata.api.domain.port.in.ProductUseCase;
import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductResponse;
import com.avidata.api.infrastructure.adapter.in.rest.mapper.ProductRestMapper;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.ProductSwagger;

import java.util.List;

/**
 * REST Controller - Input Adapter
 * Exposes HTTP endpoints
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductSwagger {
    
    private final ProductUseCase productUseCase;
    private final ProductRestMapper mapper;
    
    @Override
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Creating product with data: {}", request);
        Product product = mapper.toDomain(request);
        Product createdProduct = productUseCase.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createdProduct));
    }
    
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        return productUseCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Override
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productUseCase.findAll();
        return ResponseEntity.ok(mapper.toResponseList(products));
    }
    
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do produto",
                    required = true
            )
            @Valid @RequestBody ProductRequest request) {
        log.info("Updating product with id: {} and data: {}", id, request);
        Product product = mapper.toDomain(request);
        Product updatedProduct = productUseCase.updateProduct(id, product);
        return ResponseEntity.ok(mapper.toResponse(updatedProduct));
    }
    
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id) {
        log.info("Deleting product with id: {}", id);
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @Override
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "Quantidade a adicionar (positivo) ou remover (negativo) do estoque",
                    required = true,
                    example = "10"
            )
            @RequestParam Integer quantity) {
        log.info("Updating stock for product with id: {} by quantity: {}", id, quantity);
        Product product = productUseCase.updateStock(id, quantity);
        return ResponseEntity.ok(mapper.toResponse(product));
    }
}

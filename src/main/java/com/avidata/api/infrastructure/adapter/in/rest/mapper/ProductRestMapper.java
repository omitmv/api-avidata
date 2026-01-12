package com.avidata.api.infrastructure.adapter.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Product;
import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductRequest;
import com.avidata.api.infrastructure.adapter.in.rest.dto.ProductResponse;

import java.util.List;

/**
 * Mapper between REST DTOs and Domain entities
 */
@Component
public class ProductRestMapper {
    
    public Product toDomain(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }
    
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .available(product.isAvailable())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }
}

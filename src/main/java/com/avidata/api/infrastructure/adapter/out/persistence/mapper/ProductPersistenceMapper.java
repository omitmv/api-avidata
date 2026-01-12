package com.avidata.api.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Product;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.ProductEntity;

/**
 * Mapper between Domain entities and JPA entities
 */
@Component
public class ProductPersistenceMapper {
    
    public ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    public Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

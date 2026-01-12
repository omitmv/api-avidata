package com.avidata.api.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.avidata.api.domain.model.Product;
import com.avidata.api.domain.port.out.ProductRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.avidata.api.infrastructure.adapter.out.persistence.mapper.ProductPersistenceMapper;

import java.util.List;
import java.util.Optional;

/**
 * Persistence Adapter - Output Adapter
 * Implements the ProductRepository port using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryAdapter implements ProductRepository {
    
    private final ProductJpaRepository jpaRepository;
    private final ProductPersistenceMapper mapper;
    
    @Override
    public Product save(Product product) {
        log.info("Saving product: {}", product);
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        log.info("Finding product by id: {}", id);
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Product> findAll() {
        log.info("Finding all products");
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        log.info("Deleting product by id: {}", id);
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        log.info("Checking existence of product by id: {}", id);
        return jpaRepository.existsById(id);
    }
}

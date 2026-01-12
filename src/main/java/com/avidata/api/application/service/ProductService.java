package com.avidata.api.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avidata.api.domain.model.Product;
import com.avidata.api.domain.port.in.ProductUseCase;
import com.avidata.api.domain.port.out.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Application Service - Product Business Logic
 * Implements use cases using domain entities and ports
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService implements ProductUseCase {
    
    private final ProductRepository productRepository;
    
    @Override
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        log.info("Finding product by id: {}", id);
        return productRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.info("Finding all products");
        return productRepository.findAll();
    }
    
    @Override
    public Product updateProduct(Long id, Product product) {
        log.info("Updating product with id: {}", id);
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            log.error("Product not found with id: {}", id);
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    @Override
    public Product updateStock(Long id, Integer quantity) {
        log.info("Updating stock for product id: {} by quantity: {}", id, quantity);
        return productRepository.findById(id)
                .map(product -> {
                    if (quantity > 0) {
                        product.increaseStock(quantity);
                    } else {
                        product.reduceStock(Math.abs(quantity));
                    }
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}

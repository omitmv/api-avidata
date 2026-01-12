package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Product;

/**
 * Output Port - Product Repository
 * Defines how data should be persisted/retrieved
 */
public interface ProductRepository {
    
    Product save(Product product);
    
    Optional<Product> findById(Long id);
    
    List<Product> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}

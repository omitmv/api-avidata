package com.avidata.api.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.ProductEntity;

/**
 * Spring Data JPA Repository
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}

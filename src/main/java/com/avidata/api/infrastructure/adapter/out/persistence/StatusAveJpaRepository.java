package com.avidata.api.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.StatusAveEntity;

@Repository
public interface StatusAveJpaRepository extends JpaRepository<StatusAveEntity, Long> {
  
}

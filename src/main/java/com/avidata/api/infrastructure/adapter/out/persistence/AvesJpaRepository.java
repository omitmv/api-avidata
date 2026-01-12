package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.AvesEntity;

@Repository
public interface AvesJpaRepository extends JpaRepository<AvesEntity, Long> {
 List<AvesEntity> findByPlantelId(Long plantelId);
 List<AvesEntity> findByNomeContaining(String nome);
}

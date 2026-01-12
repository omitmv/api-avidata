package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.PlantelEntity;

@Repository
public interface PlantelJpaRepository extends JpaRepository<PlantelEntity, Long> {
  List<PlantelEntity> findByNomeContaining(String nome);
}

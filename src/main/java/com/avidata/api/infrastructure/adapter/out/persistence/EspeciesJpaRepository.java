package com.avidata.api.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.EspeciesEntity;

@Repository
public interface EspeciesJpaRepository extends JpaRepository<EspeciesEntity, Long> {
  List<EspeciesEntity> findByNomePopularContaining(String nomePopular);
}

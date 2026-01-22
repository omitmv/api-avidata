package com.avidata.api.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avidata.api.infrastructure.adapter.out.persistence.entity.TipoAnilhaEntity;

@Repository
public interface TipoAnilhaJpaRepository extends JpaRepository<TipoAnilhaEntity, Long> {
  
}

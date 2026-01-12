package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Plantel;

public interface IPlantelRepository {
  Plantel save(Plantel plantel);
  List<Plantel> findByNomeContaining(String nome);
  List<Plantel> findAll();
  Optional<Plantel> findById(Long id);
  boolean existsById(Long id);
  void deleteById(Long id);
}

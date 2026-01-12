package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Especie;

public interface IEspeciesRepository {
  Especie save(Especie especie);
  List<Especie> findByNomePopularContaining(String nomePopular);
  List<Especie> findAll();
  Optional<Especie> findById(Long id);
  boolean existsById(Long id);
  void deleteById(Long id);
}

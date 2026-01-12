package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Ave;

public interface IAvesRepository {
  Ave save(Ave ave);
  List<Ave> findByNomeContaining(String nome);
  List<Ave> findByPlantelId(Long plantelId);
  List<Ave> findByEspecieId(Long especieId);
  List<Ave> findByNumeroAnilhaContaining(String numeroAnilha);
  List<Ave> findAll();
  Optional<Ave> findById(Long id);
  boolean existsById(Long id);
  void deleteById(Long id);
}

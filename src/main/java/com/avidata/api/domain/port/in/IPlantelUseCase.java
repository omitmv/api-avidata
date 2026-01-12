package com.avidata.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Plantel;

public interface IPlantelUseCase {
  Plantel criarPlantel(Plantel plantel);
  List<Plantel> listarPlantels(String nome);
  Optional<Plantel> obterPlantelPorId(Long id);
  Plantel atualizarPlantel(Long id, Plantel plantel);
  void deletarPlantel(Long id);
}

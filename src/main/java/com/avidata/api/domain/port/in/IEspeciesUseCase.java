package com.avidata.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Especie;

public interface IEspeciesUseCase {
  Especie criarEspecie(Especie especie);
  List<Especie> listarEspecies(String nomePopular);
  Optional<Especie> obterEspeciePorId(Long id);
  Especie atualizarEspecie(Long id, Especie especie);
  void deletarEspecie(Long id);
}

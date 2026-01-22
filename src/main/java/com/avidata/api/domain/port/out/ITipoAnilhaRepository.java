package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.TipoAnilha;

public interface ITipoAnilhaRepository {
  TipoAnilha save(TipoAnilha tipoAnilha);
  Optional<TipoAnilha> findById(Long id);
  void deleteById(Long id);
  TipoAnilha updateById(Long id, TipoAnilha tipoAnilha);
  List<TipoAnilha> findAll();
}

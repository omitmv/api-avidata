package com.avidata.api.domain.port.in;

import java.util.List;

import com.avidata.api.domain.model.TipoAnilha;

public interface ITipoAnilhaUseCase {
  TipoAnilha criarTipoDeAnilha(TipoAnilha tipoAnilha);
  TipoAnilha atualizarTipoDeAnilha(Long id, TipoAnilha tipoAnilha);
  void deletarTipoDeAnilha(Long id);
  TipoAnilha obterTipoDeAnilhaPorId(Long id);
  List<TipoAnilha> listarTodosTiposDeAnilha();
}

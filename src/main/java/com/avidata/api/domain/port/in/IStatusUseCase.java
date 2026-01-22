package com.avidata.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.StatusAve;

public interface IStatusUseCase {
  StatusAve criarStatusAve(StatusAve statusAve);
  StatusAve atualizarStatusAve(Long id, StatusAve statusAve);
  Optional<StatusAve> obterStatusAvePorId(Long id);
  void deletarStatusAvePorId(Long id);
  List<StatusAve> listarTodosStatusAves();
}

package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.StatusAve;

public interface IStatusAveRepository {
  StatusAve save(StatusAve statusAve);
  Optional<StatusAve> findById(Long id);
  void deleteById(Long id);
  List<StatusAve> findAll();
  StatusAve updateById(Long id, StatusAve statusAve);
}

package com.avidata.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Ave;

/**
 * UseCase Interface - Define business operations for Ave
 */
public interface IAveUseCase {
    Ave criarAve(Ave ave);
    List<Ave> listarAves(String nome);
    List<Ave> listarAvesPorPlantel(Long plantelId);
    List<Ave> listarAvesPorEspecie(Long especieId);
    List<Ave> listarAvesPorAnilha(String numeroAnilha);
    Optional<Ave> obterAvePorId(Long id);
    Ave atualizarAve(Long id, Ave ave);
    void deletarAve(Long id);
    List<Ave> listarTodasAves();
}

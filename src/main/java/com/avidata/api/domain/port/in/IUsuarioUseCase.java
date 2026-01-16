package com.avidata.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Usuario;

public interface IUsuarioUseCase {
  Usuario salvarUsuario(Usuario usuario);
  Optional<Usuario> buscarPorUsername(String username);
  List<Usuario> listarUsuarios(String username);
  Usuario atualizarUsuario(String username, Usuario usuario);
  void deletarUsuario(String username);
  Boolean validarUsernameAndPassword(String username, String password);
}

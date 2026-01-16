package com.avidata.api.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.avidata.api.domain.model.Usuario;

public interface IUsuarioRepository {
  Usuario save(Usuario usuario);
  Optional<Usuario> findByUsername(String username);
  List<Usuario> findByUsernameContaining(String username);
  List<Usuario> findAll();
  void deleteByUsername(String username);
  Usuario update(String username, Usuario usuario);
  boolean existsByUsername(String username);
  boolean existsByUsernameAndPassword(String username, String password);
}

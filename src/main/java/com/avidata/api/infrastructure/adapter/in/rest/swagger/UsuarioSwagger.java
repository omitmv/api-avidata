package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.avidata.api.domain.model.Usuario;
import com.avidata.api.infrastructure.adapter.in.rest.dto.UsuarioRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuario", description = "API para gerenciamento de usuarios")
public interface UsuarioSwagger {
  @Operation(
    summary = "Criar novo usuario",
    description = "Cria um novo usuario no sistema com as informações fornecidas"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Usuario criado com sucesso"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Dados inválidos fornecidos"
    )
  })
  ResponseEntity<Void> createUsuario(
    @RequestBody(
      description = "Dados do usuario a ser criado",
      required = true
    )
    @Valid @org.springframework.web.bind.annotation.RequestBody
    UsuarioRequest request
  );

  @Operation(
    summary = "Atualizar usuario existente",
    description = "Atualiza um usuario existente no sistema com as informações fornecidas"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Usuario atualizado com sucesso"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Dados inválidos fornecidos"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Usuario não encontrado"
    )
  })
  ResponseEntity<Void> updateUsuario(
    @Parameter(description = "Login do usuario", required = true)
    @PathVariable String login,
    @Valid @org.springframework.web.bind.annotation.RequestBody UsuarioRequest request
  );

  @Operation(
    summary = "Deletar usuario existente",
    description = "Deleta um usuario existente no sistema com base no login fornecido"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Usuario deletado com sucesso"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Usuario não encontrado"
    )
  })
  ResponseEntity<Void> deleteUsuario(
    @Parameter(description = "Login do usuario", required = true)
    @PathVariable String login
  );

  @Operation(
    summary = "Obter usuarios ativos por login",
    description = "Recupera os detalhes dos usuarios ativos com base no login fornecido"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Usuarios ativos recuperados com sucesso"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Usuario não encontrado"
    )
  })
  ResponseEntity<List<Usuario>> getActiveUsuariosByLogin(
    @Parameter(description = "Login do usuario", required = true)
    @PathVariable String login
  );

  @Operation(
    summary = "Recuperar usuário pelo login",
    description = "Recupera os detalhes de um usuário específico com base no login fornecido"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Usuário recuperado com sucesso"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Usuário não encontrado"
    )
  })
  ResponseEntity<Usuario> getUsuarioByLogin(
    @Parameter(description = "Login do usuário", required = true)
    @PathVariable String login
  );
}

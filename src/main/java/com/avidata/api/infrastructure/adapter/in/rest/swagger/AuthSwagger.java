package com.avidata.api.infrastructure.adapter.in.rest.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.avidata.api.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.avidata.api.infrastructure.adapter.in.rest.dto.LoginRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "API de autenticação e geração de tokens JWT")
public interface AuthSwagger {
  @Operation(
    summary = "Realizar login",
    description = "Autentica o usuário e retorna um token JWT válido para acesso aos endpoints protegidos"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Login realizado com sucesso",
      content = @Content(schema = @Schema(implementation = AuthResponse.class))
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Credenciais inválidas"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Dados inválidos fornecidos"
    )
  })
  ResponseEntity<?> login(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Credenciais de login",
      required = true
    )
    @Valid @RequestBody LoginRequest loginRequest,
    HttpServletResponse response
  );

  @Operation(
    summary = "Realizar logout",
    description = "Encerra a sessão do usuário removendo o cookie JWT"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Logout realizado com sucesso"
    )
  })
  @PostMapping("/logout")
  ResponseEntity<?> logout(HttpServletResponse response);
}

package com.avidata.api.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de autenticação do usuário")
public class LoginRequest {
    
    @Schema(description = "Nome de usuário", example = "admin", required = true)
    @NotBlank(message = "Username is required")
    private String username;
    
    @Schema(description = "Senha do usuário", example = "admin", required = true)
    @NotBlank(message = "Password is required")
    private String password;
}

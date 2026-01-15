package com.avidata.api.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta de autenticação contendo o token JWT")
public class AuthResponse {
    
    @Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Tipo do token", example = "Bearer")
    private String type;
    
    @Schema(description = "ID do usuário autenticado", example = "1")
    private Long id;
    
    @Schema(description = "Nome do usuário autenticado", example = "admin")
    private String username;
    
    @Schema(description = "Email do usuário autenticado", example = "admin@example.com")
    private String email;
    
    public AuthResponse(String token, Long id, String username, String email) {
        this.token = token;
        this.type = "Bearer";
        this.id = id;
        this.username = username;
        this.email = email;
    }
}

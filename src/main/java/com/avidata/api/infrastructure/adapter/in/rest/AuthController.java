package com.avidata.api.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.avidata.api.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.avidata.api.infrastructure.adapter.out.persistence.UserJpaRepository;
import com.avidata.api.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.avidata.api.infrastructure.config.security.JwtTokenProvider;

/**
 * Authentication Controller
 * Handles login and token generation
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API de autenticação e geração de tokens JWT")
@Slf4j
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepository userRepository;
    
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
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais de login",
                    required = true
            )
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        log.info("[INFO] Tentando login para o usuário: {}", loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            
            String token = jwtTokenProvider.generateToken(authentication.getName());
            log.info("[INFO] Token gerado para o usuário: {}", authentication.getName());
            
            // Buscar dados completos do usuário
            UserEntity user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado após autenticação"));
            
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Defina como true em produção com HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(1 * 60 * 60); // 1h

            response.addCookie(cookie);
            
            return ResponseEntity.ok(new AuthResponse(
                    token, 
                    user.getId(), 
                    user.getUsername(), 
                    user.getEmail()
            ));
            
        } catch (AuthenticationException e) {
                log.error("[ERRO] Falha na autenticação para o usuário: {}", loginRequest.getUsername());
            return ResponseEntity.status(401)
                    .body("Username ou password inválidos");
        }
    }

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
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Defina como true em produção com HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // remove

        response.addCookie(cookie);

        return ResponseEntity.ok("Logout realizado");
    }

}

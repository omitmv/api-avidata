package com.avidata.api.infrastructure.adapter.in.rest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avidata.api.application.service.UsuarioService;
import com.avidata.api.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.avidata.api.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.avidata.api.infrastructure.adapter.in.rest.swagger.AuthSwagger;
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
@Slf4j
public class AuthController implements AuthSwagger {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserJpaRepository userRepository;
	private final UsuarioService usuarioService;

	@Override
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais de login", required = true) @Valid @RequestBody LoginRequest loginRequest,
			HttpServletResponse response) {
		log.info("[INFO] Tentando login para o usuário: {}", loginRequest.getUsername());
		try {
			if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
				log.error("[ERRO] Username ou password nulos");
				return ResponseEntity.badRequest().body("Username e password são obrigatórios");
			} else if (loginRequest.getUsername().isBlank() || loginRequest.getPassword().isBlank()) {
				log.error("[ERRO] Username ou password em branco");
				return ResponseEntity.badRequest().body("Username e password não podem ser em branco");
			}
			if (usuarioService.validarUsernameAndPassword(loginRequest.getUsername(),  loginRequest.getPassword()) == false) {
				log.error("[ERRO] Credenciais inválidas para o usuário: {}", loginRequest.getUsername());
				return ResponseEntity.status(401).body("Username ou password inválidos");
			}
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword()));

			String token = jwtTokenProvider.generateToken(authentication.getName());
			log.info("[INFO] Token gerado para o usuário: {}", authentication.getName());

			// Buscar dados completos do usuário
			UserEntity user = userRepository.findByUsername(authentication.getName())
					.orElseThrow(() -> new RuntimeException("Usuário não encontrado após autenticação"));

			ResponseCookie cookie = ResponseCookie.from("jwt", token)
					.httpOnly(true)
					.secure(true)
					.sameSite("None")
					.path("/")
					.maxAge(7 * 24 * 60 * 60) // 7 dias
					// NÃO definir domain, deixando o browser usar o domain da API
					// OU definir: .domain(".eastus.azurecontainerapps.io") se quiser compartilhar
					// entre subdomínios
					.build();

			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

			return ResponseEntity.ok(new AuthResponse(
					token,
					user.getId(),
					user.getUsername(),
					user.getEmail()));

		} catch (AuthenticationException e) {
			log.error("[ERRO] Falha na autenticação para o usuário: {}", loginRequest.getUsername());
			return ResponseEntity.status(401)
					.body("Username ou password inválidos");
		}
	}

	@Override
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

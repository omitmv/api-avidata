package com.avidata.api.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Authentication Filter
 * Intercepts all requests to validate JWT tokens
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, @Lazy UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // Skip JWT validation for public endpoints
        String requestPath = request.getRequestURI();
        if (isPublicEndpoint(requestPath)) {
            log.debug("Skipping JWT validation for public endpoint: {}", requestPath);
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            String jwt = extractJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt)) {
                // Validate token first
                if (!jwtTokenProvider.validateToken(jwt)) {
                    log.warn("Invalid or expired JWT token");
                    sendErrorResponse(response, "Acesso bloqueado: Token inválido ou expirado", HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                
                // Extract username and authenticate
                String username = jwtTokenProvider.extractUsername(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Successfully authenticated user: {}", username);
            }
            
            filterChain.doFilter(request, response);
            
        } catch (ExpiredJwtException ex) {
            log.error("JWT token expired: {}", ex.getMessage());
            sendErrorResponse(response, "Acesso bloqueado: Token vencido", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception ex) {
            log.error("Authentication error: {}", ex.getMessage());
            sendErrorResponse(response, "Acesso bloqueado: Erro de autenticação", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    /**
     * Check if the endpoint is public (no authentication required)
     */
    private boolean isPublicEndpoint(String requestPath) {
        return requestPath.startsWith("/api/v1/auth/") ||
               requestPath.startsWith("/swagger-ui") ||
               requestPath.startsWith("/v3/api-docs") ||
               requestPath.startsWith("/actuator/");
    }
    
    /**
     * Send JSON error response
     */
    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", status);
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", message);
        errorDetails.put("path", "");
        
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
    
    /**
     * Extract JWT token from Authorization header or Cookie
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Try Authorization header first
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        // Try cookie as fallback
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        return null;
    }
}

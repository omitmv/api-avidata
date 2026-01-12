package com.avidata.api.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger Configuration
 * Documentation: http://localhost:8080/swagger-ui.html
 * API Docs JSON: http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI servProApiOpenAPI() {
        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("Development Server");
        
        Contact contact = new Contact()
                .name("ServPro Team")
                .email("contato@servpro.com")
                .url("https://servpro.com");
        
        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
        
        Info info = new Info()
                .title("ServPro API")
                .version("1.0.0")
                .description("API RESTful desenvolvida com arquitetura hexagonal usando Spring Boot 3.4.1 e Java 21. "
                        + "Para acessar endpoints protegidos, primeiro faça login em /api/v1/auth/login para obter o token JWT.")
                .contact(contact)
                .license(license);
        
        // JWT Security Scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira o token JWT obtido no endpoint de login");
        
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");
        
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}

package com.thiagocosta.devmentor.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI devMentorOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dev Mentor API")
                        .description("API REST para planejamento de estudos e acompanhamento da evolução técnica. " +
                                   "Funcionalidades: Gerenciar usuários, tecnologias, conteúdos, planejamentos de estudo, " +
                                   "registros de estudo, projetos pessoais e cálculo de evolução técnica.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Thiago Costa")
                                .url("https://github.com/thiagocosta031083/dev-mentor-backend"))
                        .license(new License()
                                .name("MIT License")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth", 
                            new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Autenticação básica HTTP")));
    }
}

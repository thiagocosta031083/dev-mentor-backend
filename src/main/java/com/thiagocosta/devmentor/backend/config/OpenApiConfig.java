package com.thiagocosta.devmentor.backend.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
    @Bean public OpenAPI devMentorOpenAPI(){return new OpenAPI().info(new Info().title("Dev Mentor API")
            .description("API REST para planejamento de estudos e acompanhamento da evolução técnica.").version("v1"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components().addSecuritySchemes("bearerAuth",new SecurityScheme().type(SecurityScheme.Type.HTTP)
                    .scheme("bearer").bearerFormat("JWT")));}
}

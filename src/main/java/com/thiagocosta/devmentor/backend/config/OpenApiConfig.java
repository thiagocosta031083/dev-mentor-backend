package com.thiagocosta.devmentor.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI devMentorOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dev Mentor API")
                        .description("API REST para planejamento de estudos e acompanhamento da evolucao tecnica.")
                        .version("v1"));
    }
}

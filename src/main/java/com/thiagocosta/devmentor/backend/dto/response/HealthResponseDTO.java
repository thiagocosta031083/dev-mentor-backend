package com.thiagocosta.devmentor.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class HealthResponseDTO {

    @Schema(example = "UP")
    private final String status;

    @Schema(example = "Dev Mentor Backend")
    private final String application;

    public HealthResponseDTO(String status, String application) {
        this.status = status;
        this.application = application;
    }

    public String getStatus() {
        return status;
    }

    public String getApplication() {
        return application;
    }
}

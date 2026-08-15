package com.thiagocosta.devmentor.backend.dto.request;

import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import jakarta.validation.constraints.NotNull;

public class ConcluirConteudoRequestDTO {
    @NotNull private NivelDominio nivelDominio;

    public NivelDominio getNivelDominio() {
        return nivelDominio;
    }

    public void setNivelDominio(NivelDominio nivelDominio) {
        this.nivelDominio = nivelDominio;
    }
}

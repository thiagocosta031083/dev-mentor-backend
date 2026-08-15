package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import java.time.LocalDate;

public class RegistroEstudoResponseDTO {
    private final Long id;
    private final Long tecnologiaId;
    private final Long conteudoId;
    private final LocalDate data;
    private final TipoEstudo tipo;
    private final Integer tempoMinutos;
    private final String observacoes;

    public RegistroEstudoResponseDTO(RegistroEstudo r) {
        id = r.getId();
        tecnologiaId = r.getTecnologia().getId();
        conteudoId = r.getConteudoPlanejado() == null ? null : r.getConteudoPlanejado().getId();
        data = r.getData();
        tipo = r.getTipo();
        tempoMinutos = r.getTempoMinutos();
        observacoes = r.getObservacoes();
    }

    public Long getId() {
        return id;
    }

    public Long getTecnologiaId() {
        return tecnologiaId;
    }

    public Long getConteudoId() {
        return conteudoId;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoEstudo getTipo() {
        return tipo;
    }

    public Integer getTempoMinutos() {
        return tempoMinutos;
    }

    public String getObservacoes() {
        return observacoes;
    }
}

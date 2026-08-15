package com.thiagocosta.devmentor.backend.dto.request;

import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegistroEstudoRequestDTO {
    @NotNull private Long tecnologiaId;
    private Long conteudoId;
    @NotNull @PastOrPresent private LocalDate data;
    @NotNull private TipoEstudo tipo;
    @NotNull @Positive private Integer tempoMinutos;

    @Size(max = 500)
    private String observacoes;

    public Long getTecnologiaId() {
        return tecnologiaId;
    }

    public void setTecnologiaId(Long id) {
        this.tecnologiaId = id;
    }

    public Long getConteudoId() {
        return conteudoId;
    }

    public void setConteudoId(Long id) {
        this.conteudoId = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoEstudo getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstudo tipo) {
        this.tipo = tipo;
    }

    public Integer getTempoMinutos() {
        return tempoMinutos;
    }

    public void setTempoMinutos(Integer minutos) {
        this.tempoMinutos = minutos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}

package com.thiagocosta.devmentor.backend.dto.request;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class PlanoEstudoRequestDTO {
    @NotNull private Long tecnologiaId;
    @NotNull private LocalDate dataInicio;
    @NotNull private LocalDate dataFim;
    @NotNull @Positive private Double horasPlanejadasTotais;
    @NotNull @Positive private Double horasSemanais;
    @Size(max = 500) private String observacao;
    public Long getTecnologiaId() { return tecnologiaId; }
    public void setTecnologiaId(Long id) { this.tecnologiaId = id; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate data) { this.dataInicio = data; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate data) { this.dataFim = data; }
    public Double getHorasPlanejadasTotais() { return horasPlanejadasTotais; }
    public void setHorasPlanejadasTotais(Double horas) { this.horasPlanejadasTotais = horas; }
    public Double getHorasSemanais() { return horasSemanais; }
    public void setHorasSemanais(Double horas) { this.horasSemanais = horas; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}

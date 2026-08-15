package com.thiagocosta.devmentor.backend.dto.request;

import com.thiagocosta.devmentor.backend.domain.enums.TipoTecnologia;
import jakarta.validation.constraints.*;

public class TecnologiaRequestDTO {
    @NotBlank @Size(max = 100)
    private String nome;
    @NotNull
    private TipoTecnologia tipo;
    @Size(max = 500)
    private String descricao;
    @NotNull @Positive
    private Double cargaHorariaPlanejada;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoTecnologia getTipo() { return tipo; }
    public void setTipo(TipoTecnologia tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getCargaHorariaPlanejada() { return cargaHorariaPlanejada; }
    public void setCargaHorariaPlanejada(Double valor) { this.cargaHorariaPlanejada = valor; }
}

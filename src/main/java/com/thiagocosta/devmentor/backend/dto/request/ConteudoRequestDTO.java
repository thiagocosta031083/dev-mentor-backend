package com.thiagocosta.devmentor.backend.dto.request;

import com.thiagocosta.devmentor.backend.domain.enums.TipoConteudo;
import javax.validation.constraints.*;

public class ConteudoRequestDTO {
    @NotNull
    private Long tecnologiaId;
    @NotBlank @Size(max = 150)
    private String titulo;
    @NotNull
    private TipoConteudo tipo;
    @NotNull @Min(1) @Max(3)
    private Integer peso;
    public Long getTecnologiaId() { return tecnologiaId; }
    public void setTecnologiaId(Long id) { this.tecnologiaId = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public TipoConteudo getTipo() { return tipo; }
    public void setTipo(TipoConteudo tipo) { this.tipo = tipo; }
    public Integer getPeso() { return peso; }
    public void setPeso(Integer peso) { this.peso = peso; }
}

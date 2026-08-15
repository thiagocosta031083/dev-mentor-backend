package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.enums.*;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;

public class TecnologiaResponseDTO {
    private final Long id; private final String nome; private final TipoTecnologia tipo; private final String descricao;
    private final Double cargaHorariaPlanejada; private final StatusTecnologia status;
    public TecnologiaResponseDTO(Tecnologia t) {
        id=t.getId(); nome=t.getNome(); tipo=t.getTipo(); descricao=t.getDescricao();
        cargaHorariaPlanejada=t.getCargaHorariaPlanejada(); status=t.getStatus();
    }
    public Long getId(){return id;} public String getNome(){return nome;} public TipoTecnologia getTipo(){return tipo;}
    public String getDescricao(){return descricao;} public Double getCargaHorariaPlanejada(){return cargaHorariaPlanejada;}
    public StatusTecnologia getStatus(){return status;}
}

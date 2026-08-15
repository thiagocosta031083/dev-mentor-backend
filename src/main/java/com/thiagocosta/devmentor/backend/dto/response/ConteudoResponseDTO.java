package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.enums.*;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;

public class ConteudoResponseDTO {
    private final Long id; private final Long tecnologiaId; private final String titulo; private final TipoConteudo tipo;
    private final Integer peso; private final StatusConteudo status; private final NivelDominio nivelDominio;
    public ConteudoResponseDTO(ConteudoPlanejado c){id=c.getId();tecnologiaId=c.getTecnologia().getId();titulo=c.getTitulo();
        tipo=c.getTipo();peso=c.getPeso();status=c.getStatus();nivelDominio=c.getNivelDominio();}
    public Long getId(){return id;} public Long getTecnologiaId(){return tecnologiaId;} public String getTitulo(){return titulo;}
    public TipoConteudo getTipo(){return tipo;} public Integer getPeso(){return peso;} public StatusConteudo getStatus(){return status;}
    public NivelDominio getNivelDominio(){return nivelDominio;}
}

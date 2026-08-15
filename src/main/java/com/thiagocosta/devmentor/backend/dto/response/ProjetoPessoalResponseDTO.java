package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;

public class ProjetoPessoalResponseDTO {
    private final Long id; private final String nome; private final String stack; private final StatusProjeto status;
    private final String proximoPasso; private final Long tecnologiaId;
    public ProjetoPessoalResponseDTO(ProjetoPessoal p){id=p.getId();nome=p.getNome();stack=p.getStack();status=p.getStatus();
        proximoPasso=p.getProximoPasso();tecnologiaId=p.getTecnologia()==null?null:p.getTecnologia().getId();}
    public Long getId(){return id;} public String getNome(){return nome;} public String getStack(){return stack;}
    public StatusProjeto getStatus(){return status;} public String getProximoPasso(){return proximoPasso;}
    public Long getTecnologiaId(){return tecnologiaId;}
}

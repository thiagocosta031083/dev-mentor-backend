package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import java.time.LocalDate;

public class PlanoEstudoResponseDTO {
    private final Long id; private final Long tecnologiaId; private final LocalDate dataInicio; private final LocalDate dataFim;
    private final Double horasPlanejadasTotais; private final Double horasSemanais; private final String observacao;
    public PlanoEstudoResponseDTO(PlanejamentoEstudo p){id=p.getId();tecnologiaId=p.getTecnologia().getId();dataInicio=p.getDataInicio();
        dataFim=p.getDataFim();horasPlanejadasTotais=p.getHorasPlanejadasTotais();horasSemanais=p.getHorasSemanais();observacao=p.getObservacao();}
    public Long getId(){return id;} public Long getTecnologiaId(){return tecnologiaId;} public LocalDate getDataInicio(){return dataInicio;}
    public LocalDate getDataFim(){return dataFim;} public Double getHorasPlanejadasTotais(){return horasPlanejadasTotais;}
    public Double getHorasSemanais(){return horasSemanais;} public String getObservacao(){return observacao;}
}

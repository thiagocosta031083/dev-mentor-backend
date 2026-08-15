package com.thiagocosta.devmentor.backend.dto.response;

import com.thiagocosta.devmentor.backend.domain.enums.StatusEvolucao;

public class DashboardResponseDTO {
    private final String tecnologia; private final double horasPlanejadas; private final double horasRealizadas;
    private final long conteudosPlanejados; private final long conteudosConcluidos; private final double cobertura;
    private final double esforco; private final double pratica; private final double evolucao; private final double percentualEsperado;
    private final StatusEvolucao status;
    public DashboardResponseDTO(String tecnologia,double hp,double hr,long cp,long cc,double cobertura,double esforco,
                                double pratica,double evolucao,double esperado,StatusEvolucao status){this.tecnologia=tecnologia;
        horasPlanejadas=hp;horasRealizadas=hr;conteudosPlanejados=cp;conteudosConcluidos=cc;this.cobertura=cobertura;
        this.esforco=esforco;this.pratica=pratica;this.evolucao=evolucao;percentualEsperado=esperado;this.status=status;}
    public String getTecnologia(){return tecnologia;} public double getHorasPlanejadas(){return horasPlanejadas;}
    public double getHorasRealizadas(){return horasRealizadas;} public long getConteudosPlanejados(){return conteudosPlanejados;}
    public long getConteudosConcluidos(){return conteudosConcluidos;} public double getCobertura(){return cobertura;}
    public double getEsforco(){return esforco;} public double getPratica(){return pratica;} public double getEvolucao(){return evolucao;}
    public double getPercentualEsperado(){return percentualEsperado;} public StatusEvolucao getStatus(){return status;}
}

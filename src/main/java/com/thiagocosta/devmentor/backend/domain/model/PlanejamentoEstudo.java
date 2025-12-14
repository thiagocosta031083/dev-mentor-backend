package com.thiagocosta.devmentor.backend.domain.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planejamento_estudo")
public class PlanejamentoEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Quantas horas você planejou estudar nesse período.
     * Ex: 10.5 horas.
     */
    @Column(name = "horas_planejadas", nullable = false)
    private Double horasPlanejadas;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    /**
     * No MVP vamos manter simples: 1 planejamento por conteúdo.
     * Se no futuro quiser vários planejamentos por conteúdo, trocamos para ManyToOne.
     */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "conteudo_id", nullable = false, unique = true)
    private Conteudo conteudo;

    protected PlanejamentoEstudo() {
        // exigido pelo JPA
    }

    public PlanejamentoEstudo(Double horasPlanejadas, LocalDate dataInicio, LocalDate dataFim, Conteudo conteudo) {
        this.horasPlanejadas = horasPlanejadas;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.conteudo = conteudo;
    }

    public Long getId() {
        return id;
    }

    public Double getHorasPlanejadas() {
        return horasPlanejadas;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }
}

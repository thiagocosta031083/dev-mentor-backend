package com.thiagocosta.devmentor.backend.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planejamento_estudo")
public class PlanejamentoEstudo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnologia_id", nullable = false)
    private Tecnologia tecnologia;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    @Column(name = "horas_planejadas_totais", nullable = false)
    private Double horasPlanejadasTotais;
    @Column(name = "horas_semanais", nullable = false)
    private Double horasSemanais;
    @Column(length = 500)
    private String observacao;

    protected PlanejamentoEstudo() {}

    public PlanejamentoEstudo(Tecnologia tecnologia, LocalDate dataInicio, LocalDate dataFim,
                              Double horasPlanejadasTotais, Double horasSemanais, String observacao) {
        this.tecnologia = tecnologia; this.dataInicio = dataInicio; this.dataFim = dataFim;
        this.horasPlanejadasTotais = horasPlanejadasTotais; this.horasSemanais = horasSemanais; this.observacao = observacao;
    }

    public Long getId() { return id; }
    public Tecnologia getTecnologia() { return tecnologia; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public Double getHorasPlanejadasTotais() { return horasPlanejadasTotais; }
    public Double getHorasSemanais() { return horasSemanais; }
    public String getObservacao() { return observacao; }
    public void atualizar(LocalDate inicio, LocalDate fim, Double totais, Double semanais, String observacao) {
        this.dataInicio = inicio; this.dataFim = fim; this.horasPlanejadasTotais = totais;
        this.horasSemanais = semanais; this.observacao = observacao;
    }
}

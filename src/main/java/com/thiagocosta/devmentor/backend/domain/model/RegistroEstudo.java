package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_estudo")
public class RegistroEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_estudo", nullable = false)
    private LocalDate data;

    /**
     * Tempo estudado em minutos. Ex: 30, 90, 120.
     * (Minutos é ótimo para somar e comparar com planejamento depois)
     */
    @Column(name = "tempo_minutos", nullable = false)
    private Integer tempoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estudo", nullable = false, length = 20)
    private TipoEstudo tipo;

    @Column(length = 500)
    private String observacoes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "conteudo_id", nullable = false)
    private Conteudo conteudo;

    protected RegistroEstudo() {
        // exigido pelo JPA
    }

    public RegistroEstudo(LocalDate data, Integer tempoMinutos, TipoEstudo tipo, String observacoes, Conteudo conteudo) {
        this.data = data;
        this.tempoMinutos = tempoMinutos;
        this.tipo = tipo;
        this.observacoes = observacoes;
        this.conteudo = conteudo;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Integer getTempoMinutos() {
        return tempoMinutos;
    }

    public TipoEstudo getTipo() {
        return tipo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }
}

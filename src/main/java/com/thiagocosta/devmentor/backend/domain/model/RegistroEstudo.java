package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_estudo")
public class RegistroEstudo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnologia_id", nullable = false)
    private Tecnologia tecnologia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conteudo_id")
    private ConteudoPlanejado conteudoPlanejado;
    @Column(name = "data_estudo", nullable = false)
    private LocalDate data;
    @Enumerated(EnumType.STRING) @Column(name = "tipo_estudo", nullable = false, length = 20)
    private TipoEstudo tipo;
    @Column(name = "tempo_minutos", nullable = false)
    private Integer tempoMinutos;
    @Column(length = 500)
    private String observacoes;

    protected RegistroEstudo() {}

    public RegistroEstudo(Tecnologia tecnologia, ConteudoPlanejado conteudo, LocalDate data,
                          TipoEstudo tipo, Integer tempoMinutos, String observacoes) {
        this.tecnologia = tecnologia; this.conteudoPlanejado = conteudo; this.data = data;
        this.tipo = tipo; this.tempoMinutos = tempoMinutos; this.observacoes = observacoes;
    }

    public Long getId() { return id; }
    public Tecnologia getTecnologia() { return tecnologia; }
    public ConteudoPlanejado getConteudoPlanejado() { return conteudoPlanejado; }
    public LocalDate getData() { return data; }
    public TipoEstudo getTipo() { return tipo; }
    public Integer getTempoMinutos() { return tempoMinutos; }
    public String getObservacoes() { return observacoes; }
}

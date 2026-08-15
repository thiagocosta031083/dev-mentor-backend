package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;
import com.thiagocosta.devmentor.backend.domain.enums.TipoTecnologia;
import jakarta.persistence.*;

@Entity
@Table(name = "tecnologia")
public class Tecnologia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTecnologia tipo;

    @Column(length = 500)
    private String descricao;

    @Column(name = "carga_horaria_planejada", nullable = false)
    private Double cargaHorariaPlanejada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTecnologia status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected Tecnologia() {}

    public Tecnologia(
            String nome,
            TipoTecnologia tipo,
            String descricao,
            Double cargaHorariaPlanejada,
            Usuario usuario) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.cargaHorariaPlanejada = cargaHorariaPlanejada;
        this.usuario = usuario;
        this.status = StatusTecnologia.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoTecnologia getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getCargaHorariaPlanejada() {
        return cargaHorariaPlanejada;
    }

    public StatusTecnologia getStatus() {
        return status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void atualizar(
            String nome, TipoTecnologia tipo, String descricao, Double cargaHorariaPlanejada) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.cargaHorariaPlanejada = cargaHorariaPlanejada;
    }

    public void ativar() {
        this.status = StatusTecnologia.ATIVA;
    }

    public void inativar() {
        this.status = StatusTecnologia.INATIVA;
    }
}

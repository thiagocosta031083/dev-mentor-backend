package com.thiagocosta.devmentor.backend.domain.enums;

public enum NivelDominio {
    NIVEL_1(1, "Conhecimento teórico inicial"),
    NIVEL_2(2, "Consegue aplicar com ajuda"),
    NIVEL_3(3, "Aplica sozinho"),
    NIVEL_4(4, "Domina e consegue explicar");

    private final int valor;
    private final String descricao;

    NivelDominio(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }
}

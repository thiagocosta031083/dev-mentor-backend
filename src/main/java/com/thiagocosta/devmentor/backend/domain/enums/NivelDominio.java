package com.thiagocosta.devmentor.backend.domain.enums;

public enum NivelDominio {
    NIVEL_1(1, "Compreensão"),
    NIVEL_2(2, "Explicação"),
    NIVEL_3(3, "Aplicação"),
    NIVEL_4(4, "Resolução real");

    private final int valor;
    private final String descricao;

    NivelDominio(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() { return valor; }
    public String getDescricao() { return descricao; }
}

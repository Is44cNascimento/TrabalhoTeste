package com.cineRadar.testeQualidade.model;

import java.util.Objects;

public class Usuario {

    private final String nome;
    private final int idade;
    private final PerfilCinefilo perfilCinefilo;
    private boolean notificacoesHabilitadas;

    public Usuario(String nome, int idade, PerfilCinefilo perfilCinefilo) {
        this(nome, idade, perfilCinefilo, false);
    }

    public Usuario(String nome, int idade, PerfilCinefilo perfilCinefilo, boolean notificacoesHabilitadas) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome não pode ser vazio");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("idade não pode ser negativa");
        }
        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = Objects.requireNonNull(perfilCinefilo, "perfilCinefilo não pode ser null");
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public boolean isNotificacoesHabilitadas() {
        return notificacoesHabilitadas;
    }

    public void setNotificacoesHabilitadas(boolean notificacoesHabilitadas) {
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }
}

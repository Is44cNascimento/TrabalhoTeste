package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.exception.PerfilIncompletoException;
import com.cineRadar.testeQualidade.model.enums.Genero;

public class Usuario {
    private String nome;
    private int idade;
    private PerfilCinefilo perfilCinefilo;
    protected Filme[] filmeAssistido;

    public Usuario(String nome, int idade, PerfilCinefilo perfilCinefilo) {
        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = perfilCinefilo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public void setPerfilCinefilo(PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Filme[] getFilmeAssistido() {
        return filmeAssistido;
    }

}

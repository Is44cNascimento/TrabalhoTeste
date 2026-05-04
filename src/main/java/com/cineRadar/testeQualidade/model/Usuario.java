package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.exception.PerfilIncompletoException;
import com.cineRadar.testeQualidade.model.enums.Genero;

public class Usuario {
    private String nome;
    private int idade;
    private PerfilCinefilo perfilCinefilo;
    private Filme filme;
    protected String[] filmeAssistido;

    public Usuario(String nome, int idade, PerfilCinefilo perfilCinefilo) {
        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = perfilCinefilo;
    }






}

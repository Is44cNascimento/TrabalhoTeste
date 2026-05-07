package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

public class Filme {

    private String nome;
    private Genero genero;
    private ClassificacaoEtaria classificacaoEtaria;
    private Idioma idioma;
    private boolean assistido;


    public Filme(String nome, Genero genero, ClassificacaoEtaria classificacaoEtaria, Idioma idioma , boolean assistido) {
        this.nome = nome;
        this.genero = genero;
        this.classificacaoEtaria = classificacaoEtaria;
        this.idioma = idioma;
        this.assistido = false;
    }


    public boolean isAssistido() {
        return assistido;
    }

    public int getClassificacaoEtaria() {
        return classificacaoEtaria.getIdadeMinima();
    }

    public void setClassificacaoEtaria(ClassificacaoEtaria classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public boolean getAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }



}

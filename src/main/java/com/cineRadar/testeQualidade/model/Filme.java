package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.model.enums.ClassificaçãoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

public class Filme {
    private String nome;
    private Genero genero;
    private ClassificaçãoEtaria classificaçãoEtaria;
    private Idioma idioma;
    private boolean assistido;


    public Filme(String nome, Genero genero, ClassificaçãoEtaria classificaçãoEtaria, Idioma idioma ,boolean assistido) {
        this.nome = nome;
        this.genero = genero;
        this.classificaçãoEtaria = classificaçãoEtaria;
        this.idioma = idioma;
        this.assistido = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClassificaçãoEtaria getClassificaçãoEtaria() {
        return classificaçãoEtaria;
    }

    public void setClassificaçãoEtaria(ClassificaçãoEtaria classificaçãoEtaria) {
        this.classificaçãoEtaria = classificaçãoEtaria;
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

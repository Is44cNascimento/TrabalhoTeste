package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public final class Filme {

    private final String id;
    private final String titulo;
    private final int ano;
    private final int duracaoMinutos;
    private final Set<Genero> generos;
    private final ClassificacaoEtaria classificacaoEtaria;
    private final Idioma idioma;
    private final int popularidade;

    public Filme(String id,
                 String titulo,
                 int ano,
                 int duracaoMinutos,
                 Collection<Genero> generos,
                 ClassificacaoEtaria classificacaoEtaria,
                 Idioma idioma,
                 int popularidade) {
        this.id = validarTexto(id, "id");
        this.titulo = validarTexto(titulo, "titulo");
        if (ano <= 1800) {
            throw new IllegalArgumentException("Ano inválido");
        }
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duração deve ser positiva");
        }
        if (popularidade < 0 || popularidade > 100) {
            throw new IllegalArgumentException("Popularidade deve estar entre 0 e 100");
        }
        this.ano = ano;
        this.duracaoMinutos = duracaoMinutos;
        this.generos = Set.copyOf(Objects.requireNonNull(generos, "generos não pode ser null"));
        if (this.generos.isEmpty()) {
            throw new IllegalArgumentException("Filme deve possuir ao menos um gênero");
        }
        this.classificacaoEtaria = Objects.requireNonNull(classificacaoEtaria, "classificacaoEtaria não pode ser null");
        this.idioma = Objects.requireNonNull(idioma, "idioma não pode ser null");
        this.popularidade = popularidade;
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio");
        }
        return valor;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public int getPopularidade() {
        return popularidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filme filme)) {
            return false;
        }
        return id.equals(filme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

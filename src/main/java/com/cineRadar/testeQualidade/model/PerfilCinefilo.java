package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.exception.DuracaoInvalidaException;
import com.cineRadar.testeQualidade.exception.NotaInvalidaException;
import com.cineRadar.testeQualidade.exception.PesoInvalidoException;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PerfilCinefilo {

    private final Map<Genero, Double> pesosGenero;
    private int duracaoMinima;
    private int duracaoMaxima;
    private ClassificacaoEtaria classificacaoMaxima;

    private final Set<Idioma> idiomasAceitos;
    private final Set<String> filmesAssistidos;
    private final Map<String, Integer> notasFilmes;

    public PerfilCinefilo() {
        this.pesosGenero = new HashMap<>();
        this.idiomasAceitos = new HashSet<>();
        this.filmesAssistidos = new HashSet<>();
        this.notasFilmes = new HashMap<>();
    }

    public PerfilCinefilo(Genero genero, ClassificaçãoEtaria classificaçãoEtaria) {
        this.genero = genero;
        this.classificaçãoEtaria = classificaçãoEtaria;
    }

    public void definirPesoGenero(Genero genero, double peso) {
        validarPeso(peso);
        pesosGenero.put(genero, peso);
    }

    public double obterPesoGenero(Genero genero) {
        return pesosGenero.getOrDefault(genero, 0.0);
    }

    private void validarPeso(double peso) {
        if (peso < 0.0 || peso > 1.0) {
            throw new PesoInvalidoException(
                    "O peso deve estar entre 0.0 e 1.0"
            );
        }
    }



    public void definirFaixaDuracao(int minima, int maxima) {

        if (minima > maxima) {
            throw new DuracaoInvalidaException(
                    "A duração mínima não pode ser maior que a máxima"
            );
        }

        this.duracaoMinima = minima;
        this.duracaoMaxima = maxima;
    }

    public boolean duracaoEstaDentroDaFaixa(int duracao) {
        return duracao >= duracaoMinima &&
                duracao <= duracaoMaxima;
    }



    public void definirClassificacaoMaxima(ClassificacaoEtaria classificacao) {
        this.classificacaoMaxima = classificacao;
    }

    public boolean aceitaClassificacao(ClassificacaoEtaria classificacao) {

        return classificacao.getIdadeMinima()
                <= classificacaoMaxima.getIdadeMinima();
    }



    public void adicionarIdiomaAceito(Idioma idioma) {
        idiomasAceitos.add(idioma);
    }

    public boolean aceitaIdioma(Idioma idioma) {
        return idiomasAceitos.contains(idioma);
    }


    public void marcarComoAssistido(String tituloFilme) {
        filmesAssistidos.add(tituloFilme);
    }

    public boolean jaAssistiu(String tituloFilme) {
        return filmesAssistidos.contains(tituloFilme);
    }


    public void adicionarNota(String idFilme, int nota) {

        validarNota(nota);

        notasFilmes.put(idFilme, nota);
    }

    private void validarNota(int nota) {

        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException(
                    "A nota deve estar entre 1 e 5"
            );
        }
    }

    public Integer obterNota(String idFilme) {
        return notasFilmes.get(idFilme);
    }


    public Map<Genero, Double> getPesosGenero() {
        return Collections.unmodifiableMap(pesosGenero);
    }

    public int getDuracaoMinima() {
        return duracaoMinima;
    }

    public int getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public Set<Idioma> getIdiomasAceitos() {
        return Collections.unmodifiableSet(idiomasAceitos);
    }

    public Set<String> getFilmesAssistidos() {
        return Collections.unmodifiableSet(filmesAssistidos);
    }

    public Map<String, Integer> getNotasFilmes() {
        return Collections.unmodifiableMap(notasFilmes);
    }
}
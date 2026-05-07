package com.cineRadar.testeQualidade.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Recomendacao {

    public Object recomendar(Usuario usuario, Filme filme ) {
        AtomicInteger score = new AtomicInteger();

       Filme filmeRetorno = new Filme(filme.getNome(), filme.getGenero(), filme.getClassificacaoEtaria(),filme.getIdioma(), filme.isAssistido(), filme.getMinutos());

        usuario.getFilmeAssistido().forEach(f -> {
            if (f.getGenero() == filme.getGenero()) {
                score.addAndGet(25);
            }
            if (f.getIdioma() == filme.getIdioma()) {
                score.addAndGet(25);
            }
            if(f.getMinutos() > filme.getMinutos() - 10 && f.getMinutos() < filme.getMinutos() + 10){
                score.addAndGet(30);
            }
            if(f.getClassificacaoEtaria() == filme.getClassificacaoEtaria()){
                score.addAndGet(20);
            }


        });

        if (score.get() <= 50) {
            return null;
        }
        else return filmeRetorno;

    }
}

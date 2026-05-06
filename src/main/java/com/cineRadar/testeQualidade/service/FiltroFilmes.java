package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroFilmes {

    public List<Filme> filtrar(List<Filme> catalogo, PerfilCinefilo perfil) {
        if (catalogo == null || catalogo.isEmpty()) {
            return Collections.emptyList();
        }

        return catalogo.stream()
                .filter(filme -> !perfil.jaAssistiu(filme.getNome()))
                .filter(filme -> classificacaoPermitida(filme, perfil))
                .filter(filme -> perfil.aceitaIdioma(filme.getIdioma()))
                .filter(filme -> perfil.obterPesoGenero(filme.getGenero()) > 0.0)
                .collect(Collectors.toList());
    }

    private boolean classificacaoPermitida(Filme filme, PerfilCinefilo perfil) {
        return filme.getClassificacaoEtaria() <= perfil.getClassificacaoMaxima().getIdadeMinima();
    }
}
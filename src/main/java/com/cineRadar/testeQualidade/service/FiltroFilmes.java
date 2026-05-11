package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FiltroFilmes {

    public List<Filme> filtrar(List<Filme> catalogo, PerfilCinefilo perfil) {
        if (catalogo == null || catalogo.isEmpty()) {
            return Collections.emptyList();
        }

        Objects.requireNonNull(perfil, "perfil não pode ser null");

        return catalogo.stream()
                .filter(filme -> !perfil.getHistoricoAssistidos().contains(filme.getId()))
                .filter(filme -> perfil.getClassificacaoEtariaMaxima().aceita(filme.getClassificacaoEtaria()))
                .filter(filme -> perfil.getIdiomasAceitos().contains(filme.getIdioma()))
                .filter(filme -> filme.getGeneros().stream().noneMatch(genero -> perfil.getPesoGenero(genero) == 0.0))
                .toList();
    }
}

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
              .filter(filme -> !perfil.getHistoricoAssistidos().contains(filme.getId()))

                .filter(filme -> classificacaoPermitida(filme, perfil))
                .filter(filme -> perfil.getIdiomasAceitos().contains(filme.getIdioma()))
                .filter(filme -> filme.getGeneros().stream()
                        .anyMatch(genero -> perfil.getPesoGenero(genero) > 0.0)
                )
                .collect(Collectors.toList());
    }

    private boolean classificacaoPermitida(Filme filme, PerfilCinefilo perfil) {

        return filme.getClassificacaoEtaria().getIdadeMinima() <= perfil.getClassificacaoEtariaMaxima().getIdadeMinima();
    }
}

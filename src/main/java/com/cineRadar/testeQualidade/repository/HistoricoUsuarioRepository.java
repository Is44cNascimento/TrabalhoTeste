package com.cineRadar.testeQualidade.repository;

import com.cineRadar.testeQualidade.model.Recomendacao;
import com.cineRadar.testeQualidade.model.Usuario;

import java.util.List;

public interface HistoricoUsuarioRepository {

    void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacoes);
}
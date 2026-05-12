package com.cineRadar.testeQualidade.service;


import com.cineRadar.testeQualidade.model.Filme;

import java.util.List;

public interface CatalogoFilmesAPI {

    List<Filme> buscarTodos();
}
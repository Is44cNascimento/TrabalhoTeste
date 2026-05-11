package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

import java.util.List;
import java.util.Set;

public class CatalogoMock implements CatalogoFilmesAPI {

    @Override
    public List<Filme> buscarTodos() {
        return List.of(
                filme("amelie", "O Fabuloso Destino de Amélie Poulain", 2001, 122, Set.of(Genero.ROMANCE, Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.FRANCES, 89),
                filme("before-sunrise", "Antes do Amanhecer", 1995, 101, Set.of(Genero.ROMANCE, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 91),
                filme("brooklyn", "Brooklyn", 2015, 111, Set.of(Genero.ROMANCE, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 84),
                filme("sing-street", "Sing Street", 2016, 106, Set.of(Genero.DRAMA, Genero.ROMANCE, Genero.COMEDIA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 86),
                filme("la-la-land", "La La Land", 2016, 128, Set.of(Genero.ROMANCE, Genero.DRAMA, Genero.COMEDIA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 92),
                filme("little-miss-sunshine", "Pequena Miss Sunshine", 2006, 101, Set.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 88),
                filme("about-time", "Questão de Tempo", 2013, 123, Set.of(Genero.ROMANCE, Genero.COMEDIA, Genero.FANTASIA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 90),
                filme("your-name", "Your Name", 2016, 106, Set.of(Genero.ROMANCE, Genero.ANIMACAO, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.JAPONES, 94),
                filme("coco", "Viva - A Vida é uma Festa", 2017, 105, Set.of(Genero.ANIMACAO, Genero.AVENTURA, Genero.FANTASIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 95),
                filme("inside-out", "Divertida Mente", 2015, 95, Set.of(Genero.ANIMACAO, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 96),
                filme("up", "Up - Altas Aventuras", 2009, 96, Set.of(Genero.ANIMACAO, Genero.AVENTURA, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 91),
                filme("parasite", "Parasita", 2019, 132, Set.of(Genero.DRAMA, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.COREANO, 98),
                filme("arrival", "A Chegada", 2016, 116, Set.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.DEZ, Idioma.INGLES, 87),
                filme("interstellar", "Interestelar", 2014, 169, Set.of(Genero.FICCAO_CIENTIFICA, Genero.AVENTURA, Genero.DRAMA), ClassificacaoEtaria.DEZ, Idioma.INGLES, 97),
                filme("blade-runner-2049", "Blade Runner 2049", 2017, 164, Set.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 88),
                filme("inception", "A Origem", 2010, 148, Set.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO, Genero.SUSPENSE), ClassificacaoEtaria.DEZ, Idioma.INGLES, 96),
                filme("mad-max-fury-road", "Mad Max: Estrada da Fúria", 2015, 120, Set.of(Genero.ACAO, Genero.AVENTURA), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 93),
                filme("spirited-away", "A Viagem de Chihiro", 2001, 125, Set.of(Genero.ANIMACAO, Genero.FANTASIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.JAPONES, 95),
                filme("paddington-2", "Paddington 2", 2017, 104, Set.of(Genero.COMEDIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 85),
                filme("knives-out", "Entre Facas e Segredos", 2019, 130, Set.of(Genero.CRIME, Genero.COMEDIA, Genero.SUSPENSE), ClassificacaoEtaria.DOZE, Idioma.INGLES, 90),
                filme("the-batman", "The Batman", 2022, 176, Set.of(Genero.ACAO, Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 89),
                filme("the-godfather", "O Poderoso Chefão", 1972, 175, Set.of(Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.INGLES, 99),
                filme("the-dark-knight", "Batman: O Cavaleiro das Trevas", 2008, 152, Set.of(Genero.ACAO, Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 99),
                filme("shrek", "Shrek", 2001, 90, Set.of(Genero.ANIMACAO, Genero.COMEDIA, Genero.FANTASIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 89),
                filme("ratatouille", "Ratatouille", 2007, 111, Set.of(Genero.ANIMACAO, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 94),
                filme("the-truman-show", "O Show de Truman", 1998, 103, Set.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 90),
                filme("whiplash", "Whiplash", 2014, 107, Set.of(Genero.DRAMA), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 92),
                filme("frozen", "Frozen", 2013, 102, Set.of(Genero.ANIMACAO, Genero.FANTASIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 82),
                filme("wall-e", "WALL-E", 2008, 98, Set.of(Genero.ANIMACAO, Genero.FICCAO_CIENTIFICA, Genero.ROMANCE), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 93),
                filme("meia-noite-em-paris", "Meia-Noite em Paris", 2011, 94, Set.of(Genero.ROMANCE, Genero.COMEDIA, Genero.FANTASIA), ClassificacaoEtaria.DEZ, Idioma.INGLES, 83),
                filme("roma", "Roma", 2018, 135, Set.of(Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.ESPANHOL, 84),
                filme("toy-story-3", "Toy Story 3", 2010, 103, Set.of(Genero.ANIMACAO, Genero.AVENTURA, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 92)
        );
    }

    private Filme filme(String id,
                        String titulo,
                        int ano,
                        int duracao,
                        Set<Genero> generos,
                        ClassificacaoEtaria classificacaoEtaria,
                        Idioma idioma,
                        int popularidade) {
        return new Filme(id, titulo, ano, duracao, generos, classificacaoEtaria, idioma, popularidade);
    }
}

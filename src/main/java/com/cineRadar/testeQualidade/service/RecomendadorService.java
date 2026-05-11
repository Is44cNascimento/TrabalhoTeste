package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.Recomendacao;
import com.cineRadar.testeQualidade.model.Usuario;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;
import com.cineRadar.testeQualidade.repository.HistoricoUsuarioRepository;
import com.cineRadar.testeQualidade.util.GeradorAleatorio;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class RecomendadorService {

    private final CatalogoFilmesAPI catalogoFilmesAPI;
    private final HistoricoUsuarioRepository historicoUsuarioRepository;
    private final NotificadorPush notificadorPush;
    private final GeradorAleatorio geradorAleatorio;
    private final CalculadoraScore calculadoraScore;
    private final FiltroFilmes filtroFilmes;

    public RecomendadorService(CatalogoFilmesAPI catalogoFilmesAPI,
                               HistoricoUsuarioRepository historicoUsuarioRepository,
                               NotificadorPush notificadorPush,
                               GeradorAleatorio geradorAleatorio,
                               CalculadoraScore calculadoraScore,
                               FiltroFilmes filtroFilmes) {
        this.catalogoFilmesAPI = Objects.requireNonNull(catalogoFilmesAPI, "catalogoFilmesAPI não pode ser null");
        this.historicoUsuarioRepository = Objects.requireNonNull(historicoUsuarioRepository,
                "historicoUsuarioRepository não pode ser null");
        this.notificadorPush = Objects.requireNonNull(notificadorPush, "notificadorPush não pode ser null");
        this.geradorAleatorio = Objects.requireNonNull(geradorAleatorio, "geradorAleatorio não pode ser null");
        this.calculadoraScore = Objects.requireNonNull(calculadoraScore, "calculadoraScore não pode ser null");
        this.filtroFilmes = Objects.requireNonNull(filtroFilmes, "filtroFilmes não pode ser null");
    }

    /**
     * Gera uma lista ordenada das melhores recomendações para o usuário informado.
     * Em caso de falha no catálogo externo, retorna lista vazia e não notifica o usuário.
     *
     * @param usuario usuário que receberá as recomendações
     * @param topN quantidade máxima de itens desejada
     * @return lista nunca nula com até {@code topN} recomendações
     */
    public List<Recomendacao> recomendar(Usuario usuario, int topN) {
        if (topN <= 0) {
            return Collections.emptyList();
        }

        List<Filme> catalogo = buscarCatalogoSeguramente();
        if (catalogo.isEmpty()) {
            return Collections.emptyList();
        }

        List<Filme> elegiveis = filtroFilmes.filtrar(catalogo, usuario.getPerfilCinefilo());
        List<RecomendacaoOrdenavel> ordenaveis = elegiveis.stream()
                .map(filme -> new RecomendacaoOrdenavel(
                        new Recomendacao(
                                filme,
                                calculadoraScore.calcularScore(usuario, filme, catalogo),
                                calculadoraScore.gerarJustificativa(usuario, filme, catalogo)
                        ),
                        geradorAleatorio.sortearInteiro(0, Integer.MAX_VALUE)
                ))
                .sorted(Comparator.comparingInt((RecomendacaoOrdenavel item) -> item.recomendacao().getScore()).reversed()
                        .thenComparing(Comparator.comparingInt(
                                (RecomendacaoOrdenavel item) -> item.recomendacao().getFilme().getPopularidade()).reversed())
                        .thenComparing(Comparator.comparingInt(RecomendacaoOrdenavel::desempateAleatorio).reversed()))
                .toList();

        List<Recomendacao> recomendacoes = ordenaveis.stream()
                .limit(topN)
                .map(RecomendacaoOrdenavel::recomendacao)
                .toList();

        historicoUsuarioRepository.registrarRecomendacao(usuario, recomendacoes);
        notificarSemQuebra(usuario, recomendacoes);
        return recomendacoes;
    }

    /**
     * Retorna uma recomendação aleatória dentre os filmes elegíveis para o usuário.
     * Se não houver candidatos, retorna uma recomendação placeholder em vez de {@code null}.
     *
     * @param usuario usuário que receberá a recomendação
     * @return recomendação única nunca nula
     */
    public Recomendacao recomendarAleatorio(Usuario usuario) {
        List<Filme> catalogo = buscarCatalogoSeguramente();
        if (catalogo.isEmpty()) {
            return recomendacaoSemResultado();
        }

        List<Filme> elegiveis = filtroFilmes.filtrar(catalogo, usuario.getPerfilCinefilo());
        if (elegiveis.isEmpty()) {
            return recomendacaoSemResultado();
        }

        int indice = geradorAleatorio.sortearInteiro(0, elegiveis.size());
        Filme filme = elegiveis.get(indice);
        Recomendacao recomendacao = new Recomendacao(
                filme,
                calculadoraScore.calcularScore(usuario, filme, catalogo),
                calculadoraScore.gerarJustificativa(usuario, filme, catalogo)
        );
        List<Recomendacao> recomendacoes = List.of(recomendacao);
        historicoUsuarioRepository.registrarRecomendacao(usuario, recomendacoes);
        notificarSemQuebra(usuario, recomendacoes);
        return recomendacao;
    }

    private List<Filme> buscarCatalogoSeguramente() {
        try {
            List<Filme> catalogo = catalogoFilmesAPI.buscarTodos();
            return catalogo == null ? Collections.emptyList() : catalogo;
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }
    }

    private void notificarSemQuebra(Usuario usuario, List<Recomendacao> recomendacoes) {
        if (!usuario.isNotificacoesHabilitadas()) {
            return;
        }
        try {
            notificadorPush.enviar(usuario, recomendacoes);
        } catch (RuntimeException ignored) {
            // Falha de notificação não deve interromper a recomendação.
        }
    }

    private Recomendacao recomendacaoSemResultado() {
        Filme placeholder = new Filme(
                "sem-recomendacao",
                "Sem recomendação disponível",
                2026,
                1,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.LIVRE,
                Idioma.PORTUGUES,
                0
        );
        return new Recomendacao(placeholder, 0, "Nenhum filme elegível encontrado.");
    }

    private record RecomendacaoOrdenavel(Recomendacao recomendacao, int desempateAleatorio) {
    }
}

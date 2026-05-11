package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;
import com.cineRadar.testeQualidade.model.Recomendacao;
import com.cineRadar.testeQualidade.model.Usuario;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;
import com.cineRadar.testeQualidade.repository.HistoricoUsuarioRepository;
import com.cineRadar.testeQualidade.util.GeradorAleatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unitario")
@DisplayName("RecomendadorService")
class RecomendadorServiceTest {

    @Mock
    private CatalogoFilmesAPI catalogoFilmesAPI;
    @Mock
    private HistoricoUsuarioRepository historicoUsuarioRepository;
    @Mock
    private NotificadorPush notificadorPush;
    @Mock
    private GeradorAleatorio geradorAleatorio;

    private CalculadoraScore calculadoraScore;
    private FiltroFilmes filtroFilmes;
    private Usuario usuario;
    private Filme romancePopular;
    private Filme romancePopularMenor;
    private Filme dramaMedio;

    @BeforeEach
    void setUp() {
        calculadoraScore = new CalculadoraScore();
        filtroFilmes = new FiltroFilmes();

        PerfilCinefilo perfil = new PerfilCinefilo();
        perfil.definirPesoGenero(Genero.ROMANCE, 1.0);
        perfil.definirPesoGenero(Genero.DRAMA, 0.9);
        perfil.definirFaixaDuracao(90, 130);
        perfil.definirClassificacaoEtariaMaxima(ClassificacaoEtaria.DOZE);
        perfil.adicionarIdiomaAceito(Idioma.INGLES);
        perfil.registrarNota("assistido", 5);
        perfil.marcarComoAssistido("assistido");

        usuario = new Usuario("Maria", 27, perfil, true);
        romancePopular = filme("top-1", "Top 1", 95, Set.of(Genero.ROMANCE, Genero.DRAMA));
        romancePopularMenor = filme("top-2", "Top 2", 90, Set.of(Genero.ROMANCE, Genero.DRAMA));
        dramaMedio = filme("top-3", "Top 3", 80, Set.of(Genero.DRAMA));
    }

    @Nested
    @DisplayName("fluxo principal")
    class FluxoPrincipal {

        @Test
        @DisplayName("deve ordenar por score e popularidade e registrar histórico")
        void deveOrdenarERegistrarHistorico() {
            when(catalogoFilmesAPI.buscarTodos()).thenReturn(List.of(
                    filme("assistido", "Já Assistido", 70, Set.of(Genero.ROMANCE)),
                    romancePopularMenor,
                    dramaMedio,
                    romancePopular
            ));
            when(geradorAleatorio.sortearInteiro(any(Integer.class), any(Integer.class))).thenReturn(10, 20, 30);

            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, calculadoraScore, filtroFilmes);

            List<Recomendacao> recomendacoes = service.recomendar(usuario, 2);

            ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);
            verify(historicoUsuarioRepository).registrarRecomendacao(eq(usuario), captor.capture());
            verify(notificadorPush).enviar(eq(usuario), eq(recomendacoes));
            assertAll(
                    () -> assertEquals(2, recomendacoes.size()),
                    () -> assertEquals("Top 1", recomendacoes.get(0).getFilme().getTitulo()),
                    () -> assertEquals("Top 2", recomendacoes.get(1).getFilme().getTitulo()),
                    () -> assertEquals(recomendacoes, captor.getValue())
            );
        }

        @Test
        @DisplayName("deve usar desempate aleatório quando score e popularidade forem iguais")
        void deveUsarDesempateAleatorio() {
            Filme empateA = filme("empate-a", "Empate A", 90, Set.of(Genero.ROMANCE));
            Filme empateB = filme("empate-b", "Empate B", 90, Set.of(Genero.ROMANCE));
            when(catalogoFilmesAPI.buscarTodos()).thenReturn(List.of(empateA, empateB));
            when(geradorAleatorio.sortearInteiro(any(Integer.class), any(Integer.class))).thenReturn(1, 9);

            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, calculadoraScore, filtroFilmes);

            List<Recomendacao> recomendacoes = service.recomendar(usuario, 2);

            assertEquals("Empate B", recomendacoes.get(0).getFilme().getTitulo());
        }

        @Test
        @DisplayName("deve retornar lista vazia quando API falhar")
        void deveRetornarListaVaziaQuandoApiFalhar() {
            when(catalogoFilmesAPI.buscarTodos()).thenThrow(new IllegalStateException("falhou"));
            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, calculadoraScore, filtroFilmes);

            List<Recomendacao> recomendacoes = service.recomendar(usuario, 3);

            assertTrue(recomendacoes.isEmpty());
            verify(historicoUsuarioRepository, never()).registrarRecomendacao(any(), any());
            verify(notificadorPush, never()).enviar(any(), any());
        }

        @Test
        @DisplayName("deve continuar quando notificação falhar")
        void deveContinuarQuandoNotificacaoFalhar() {
            when(catalogoFilmesAPI.buscarTodos()).thenReturn(List.of(romancePopular));
            when(geradorAleatorio.sortearInteiro(any(Integer.class), any(Integer.class))).thenReturn(5);
            doThrow(new RuntimeException("push indisponível")).when(notificadorPush).enviar(any(), any());
            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, calculadoraScore, filtroFilmes);

            assertDoesNotThrow(() -> service.recomendar(usuario, 1));
            verify(historicoUsuarioRepository).registrarRecomendacao(eq(usuario), any());
        }

        @Test
        @DisplayName("deve usar spy da calculadora ao montar recomendações")
        void deveUsarSpyDaCalculadoraAoMontarRecomendacoes() {
            when(catalogoFilmesAPI.buscarTodos()).thenReturn(List.of(romancePopular, romancePopularMenor));
            when(geradorAleatorio.sortearInteiro(any(Integer.class), any(Integer.class))).thenReturn(3, 4);
            CalculadoraScore spyCalculadora = spy(new CalculadoraScore());
            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, spyCalculadora, filtroFilmes);

            service.recomendar(usuario, 2);

            verify(spyCalculadora, times(2)).calcularScore(eq(usuario), any(Filme.class), any());
        }

        @Test
        @DisplayName("deve recomendar filme aleatório elegível")
        void deveRecomendarFilmeAleatorioElegivel() {
            when(catalogoFilmesAPI.buscarTodos()).thenReturn(List.of(romancePopular, romancePopularMenor, dramaMedio));
            when(geradorAleatorio.sortearInteiro(any(Integer.class), any(Integer.class))).thenReturn(1);
            RecomendadorService service = new RecomendadorService(catalogoFilmesAPI, historicoUsuarioRepository,
                    notificadorPush, geradorAleatorio, calculadoraScore, filtroFilmes);

            Recomendacao recomendacao = service.recomendarAleatorio(usuario);

            assertEquals("Top 2", recomendacao.getFilme().getTitulo());
            verify(historicoUsuarioRepository).registrarRecomendacao(eq(usuario), eq(List.of(recomendacao)));
        }
    }

    @Nested
    @Tag("integracao")
    @DisplayName("integração com catálogo real")
    class Integracao {

        @Test
        @DisplayName("deve recomendar a ordem esperada para o perfil da Maria")
        void deveRecomendarOrdemEsperadaParaMaria() {
            CatalogoFilmesAPI catalogoReal = new CatalogoMock();
            CalculadoraScore calculadoraReal = new CalculadoraScore();
            FiltroFilmes filtroReal = new FiltroFilmes();
            GeradorAleatorio geradorDeterministico = (min, max) -> min;
            RecomendadorService service = new RecomendadorService(catalogoReal, historicoUsuarioRepository,
                    notificadorPush, geradorDeterministico, calculadoraReal, filtroReal);

            PerfilCinefilo perfilMaria = new PerfilCinefilo();
            perfilMaria.definirPesoGenero(Genero.DRAMA, 1.0);
            perfilMaria.definirPesoGenero(Genero.ROMANCE, 0.95);
            perfilMaria.definirPesoGenero(Genero.COMEDIA, 0.75);
            perfilMaria.definirPesoGenero(Genero.FANTASIA, 0.30);
            perfilMaria.definirPesoGenero(Genero.ANIMACAO, 0.20);
            perfilMaria.definirFaixaDuracao(90, 130);
            perfilMaria.definirClassificacaoEtariaMaxima(ClassificacaoEtaria.DOZE);
            perfilMaria.adicionarIdiomaAceito(Idioma.INGLES);
            perfilMaria.adicionarIdiomaAceito(Idioma.ESPANHOL);
            perfilMaria.marcarComoAssistido("amelie");
            perfilMaria.marcarComoAssistido("la-la-land");
            perfilMaria.registrarNota("amelie", 5);
            perfilMaria.registrarNota("la-la-land", 4);
            Usuario maria = new Usuario("Maria", 29, perfilMaria, false);

            List<Recomendacao> recomendacoes = service.recomendar(maria, 3);
            String[] titulos = recomendacoes.stream().map(r -> r.getFilme().getTitulo()).toArray(String[]::new);

            assertArrayEquals(new String[]{"Antes do Amanhecer", "Brooklyn", "Sing Street"}, titulos);
        }
    }

    private Filme filme(String id, String titulo, int popularidade, Set<Genero> generos) {
        return new Filme(id, titulo, 2024, 110, generos, ClassificacaoEtaria.DOZE, Idioma.INGLES, popularidade);
    }
}

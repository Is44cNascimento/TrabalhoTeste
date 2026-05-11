package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
@DisplayName("FiltroFilmes")
class FiltroFilmesTest {

    private FiltroFilmes filtroFilmes;
    private PerfilCinefilo perfil;
    private Filme elegivel;

    @BeforeEach
    void setUp() {
        filtroFilmes = new FiltroFilmes();
        perfil = new PerfilCinefilo();
        perfil.definirClassificacaoEtariaMaxima(ClassificacaoEtaria.DOZE);
        perfil.definirFaixaDuracao(80, 140);
        perfil.adicionarIdiomaAceito(Idioma.INGLES);
        perfil.definirPesoGenero(Genero.DRAMA, 1.0);
        perfil.definirPesoGenero(Genero.ROMANCE, 0.8);
        elegivel = new Filme("elegivel", "Elegível", 2020, 110,
                Set.of(Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80);
    }

    @Nested
    @DisplayName("regras de filtragem")
    class RegrasFiltragem {

        @Test
        @DisplayName("deve retornar lista vazia para catálogo vazio")
        void deveRetornarListaVaziaParaCatalogoVazio() {
            assertTrue(filtroFilmes.filtrar(List.of(), perfil).isEmpty());
        }

        @Test
        @DisplayName("deve remover filme já assistido")
        void deveRemoverFilmeJaAssistido() {
            perfil.marcarComoAssistido("elegivel");

            List<Filme> filtrados = filtroFilmes.filtrar(List.of(elegivel), perfil);

            assertTrue(filtrados.isEmpty());
        }

        @Test
        @DisplayName("deve remover filme com classificação acima da aceita")
        void deveRemoverFilmeAcimaDaClassificacaoAceita() {
            Filme proibido = new Filme("proibido", "Proibido", 2020, 100,
                    Set.of(Genero.DRAMA), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 70);

            List<Filme> filtrados = filtroFilmes.filtrar(List.of(proibido), perfil);

            assertFalse(filtrados.contains(proibido));
        }

        @Test
        @DisplayName("deve remover filme em idioma não aceito")
        void deveRemoverFilmeEmIdiomaNaoAceito() {
            Filme estrangeiro = new Filme("estrangeiro", "Estrangeiro", 2020, 100,
                    Set.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.JAPONES, 70);

            List<Filme> filtrados = filtroFilmes.filtrar(List.of(estrangeiro), perfil);

            assertTrue(filtrados.isEmpty());
        }

        @Test
        @DisplayName("deve remover filme com gênero de peso zero")
        void deveRemoverFilmeComGeneroDePesoZero() {
            Filme terror = new Filme("terror", "Terror", 2020, 100,
                    Set.of(Genero.TERROR), ClassificacaoEtaria.DOZE, Idioma.INGLES, 60);

            List<Filme> filtrados = filtroFilmes.filtrar(List.of(terror), perfil);

            assertTrue(filtrados.isEmpty());
        }

        @Test
        @DisplayName("deve manter apenas filmes elegíveis")
        void deveManterApenasFilmesElegiveis() {
            List<Filme> filtrados = filtroFilmes.filtrar(List.of(elegivel), perfil);

            assertEquals(List.of(elegivel), filtrados);
        }
    }
}

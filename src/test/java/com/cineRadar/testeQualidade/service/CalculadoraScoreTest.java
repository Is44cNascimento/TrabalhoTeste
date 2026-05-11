package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;
import com.cineRadar.testeQualidade.model.Usuario;
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
@DisplayName("CalculadoraScore")
class CalculadoraScoreTest {

    private CalculadoraScore calculadoraScore;
    private PerfilCinefilo perfil;
    private Usuario usuario;
    private Filme filmeBase;
    private Filme historicoBemAvaliado;
    private Filme historicoMalAvaliado;

    @BeforeEach
    void setUp() {
        calculadoraScore = new CalculadoraScore();
        perfil = new PerfilCinefilo();
        perfil.definirPesoGenero(Genero.DRAMA, 1.0);
        perfil.definirPesoGenero(Genero.ROMANCE, 0.9);
        perfil.definirPesoGenero(Genero.COMEDIA, 0.6);
        perfil.definirPesoGenero(Genero.FICCAO_CIENTIFICA, 0.2);
        perfil.definirFaixaDuracao(90, 120);
        perfil.registrarNota("hist-1", 5);
        perfil.registrarNota("hist-2", 1);
        usuario = new Usuario("Maria", 28, perfil);
        filmeBase = new Filme("candidato", "Candidato", 2024, 110,
                Set.of(Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80);
        historicoBemAvaliado = new Filme("hist-1", "Histórico 1", 2020, 100,
                Set.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 70);
        historicoMalAvaliado = new Filme("hist-2", "Histórico 2", 2020, 100,
                Set.of(Genero.FICCAO_CIENTIFICA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 70);
    }

    @Nested
    @DisplayName("cálculo de score")
    class CalculoScore {

        @Test
        @DisplayName("deve gerar score alto para filme muito aderente")
        void deveGerarScoreAltoParaFilmeAderente() {
            int score = calculadoraScore.calcularScore(usuario, filmeBase, List.of(filmeBase, historicoBemAvaliado, historicoMalAvaliado));

            assertTrue(score >= 90);
        }

        @Test
        @DisplayName("deve reduzir score quando duração fica fora da faixa")
        void deveReduzirScorePorDuracaoForaDaFaixa() {
            Filme filmeCurto = new Filme("curto", "Curto", 2024, 40,
                    Set.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80);

            int scoreCurto = calculadoraScore.calcularScore(usuario, filmeCurto, List.of(historicoBemAvaliado));
            int scoreIdeal = calculadoraScore.calcularScore(usuario,
                    new Filme("ideal", "Ideal", 2024, 100, Set.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80),
                    List.of(historicoBemAvaliado));

            assertTrue(scoreIdeal > scoreCurto);
        }

        @Test
        @DisplayName("deve aplicar bônus de afinidade apenas quando há gêneros em comum")
        void deveAplicarBonusDeAfinidadeApenasComGenerosEmComum() {
            Filme semGeneroEmComum = new Filme("sem-afinidade", "Sem Afinidade", 2024, 100,
                    Set.of(Genero.TERROR), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80);

            int scoreComAfinidade = calculadoraScore.calcularScore(usuario, filmeBase, List.of(historicoBemAvaliado, historicoMalAvaliado));
            int scoreSemAfinidade = calculadoraScore.calcularScore(usuario, semGeneroEmComum, List.of(historicoBemAvaliado, historicoMalAvaliado));

            assertTrue(scoreComAfinidade > scoreSemAfinidade);
        }

        @Test
        @DisplayName("deve limitar score ao intervalo de zero a cem")
        void deveLimitarScoreAoIntervaloValido() {
            PerfilCinefilo perfilPerfeito = new PerfilCinefilo();
            perfilPerfeito.definirPesoGenero(Genero.DRAMA, 1.0);
            perfilPerfeito.definirFaixaDuracao(100, 100);
            perfilPerfeito.registrarNota("hist-1", 5);
            Usuario usuarioPerfeito = new Usuario("Ana", 30, perfilPerfeito);
            Filme perfeito = new Filme("perfeito", "Perfeito", 2024, 100,
                    Set.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 100);

            int score = calculadoraScore.calcularScore(usuarioPerfeito, perfeito, List.of(historicoBemAvaliado, perfeito));

            assertAll(
                    () -> assertTrue(score >= 0),
                    () -> assertTrue(score <= 100)
            );
        }

        @Test
        @DisplayName("deve gerar justificativa não nula")
        void deveGerarJustificativaNaoNula() {
            String justificativa = calculadoraScore.gerarJustificativa(usuario, filmeBase, List.of(historicoBemAvaliado));

            assertAll(
                    () -> assertNotNull(justificativa),
                    () -> assertFalse(justificativa.isBlank()),
                    () -> assertTrue(justificativa.contains("popularidade"))
            );
        }
    }
}

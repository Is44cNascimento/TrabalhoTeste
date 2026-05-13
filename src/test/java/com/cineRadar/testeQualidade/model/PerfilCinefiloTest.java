package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.exception.DuracaoInvalidaException;
import com.cineRadar.testeQualidade.exception.NotaInvalidaException;
import com.cineRadar.testeQualidade.exception.PesoInvalidoException;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
@DisplayName("PerfilCinefilo")
class PerfilCinefiloTest {

    private PerfilCinefilo perfil;

    @BeforeEach
    void setUp() {
        perfil = new PerfilCinefilo();
        perfil.definirFaixaDuracao(90, 130);
        perfil.definirClassificacaoEtariaMaxima(ClassificacaoEtaria.DOZE);
        perfil.adicionarIdiomaAceito(Idioma.INGLES);
        perfil.definirPesoGenero(Genero.DRAMA, 1.0);
    }

    @Nested
    @DisplayName("validação de pesos")
    class ValidacaoPeso {

        @ParameterizedTest
        @CsvSource({"-0.1", "1.1"})
        @DisplayName("deve rejeitar peso fora do intervalo permitido")
        void deveRejeitarPesoForaDoIntervalo(double peso) {
            assertThrows(PesoInvalidoException.class, () -> perfil.definirPesoGenero(Genero.COMEDIA, peso));
        }

        @Test
        @DisplayName("deve aceitar peso válido")
        void deveAceitarPesoValido() {
            assertDoesNotThrow(() -> perfil.definirPesoGenero(Genero.COMEDIA, 0.6));
            assertEquals(0.6, perfil.getPesoGenero(Genero.COMEDIA));
        }
    }

    @Nested
    @DisplayName("validação de preferências")
    class ValidacaoPreferencias {

        @Test
        @DisplayName("deve rejeitar faixa de duração invertida")
        void deveRejeitarFaixaInvertida() {
            assertThrows(DuracaoInvalidaException.class, () -> perfil.definirFaixaDuracao(150, 100));
        }

        @ParameterizedTest
        @CsvSource({"0", "6"})
        @DisplayName("deve rejeitar notas fora do intervalo")
        void deveRejeitarNotaForaDoIntervalo(int nota) {
            assertThrows(NotaInvalidaException.class, () -> perfil.registrarNota("filme-1", nota));
        }

        @Test
        @DisplayName("deve retornar nota cadastrada ou nulo quando ausente")
        void deveRetornarNotaOuNulo() {
            perfil.registrarNota("filme-1", 5);

            assertAll(
                    () -> assertEquals(5, perfil.getNotaPara("filme-1")),
                    () -> assertNull(perfil.getNotaPara("desconhecido")),
                    () -> assertNotNull(perfil.getNotasPorFilme())
            );
        }

        @Test
        @DisplayName("deve expor coleções imutáveis")
        void deveExporColecoesImutaveis() {
            assertAll(
                    () -> assertThrows(UnsupportedOperationException.class,
                            () -> perfil.getIdiomasAceitos().add(Idioma.PORTUGUES)),
                    () -> assertThrows(UnsupportedOperationException.class,
                            () -> perfil.getHistoricoAssistidos().add("novo")),
                    () -> assertThrows(UnsupportedOperationException.class,
                            () -> perfil.getPesosGenero().put(Genero.ACAO, 0.2))
            );
        }

        @Test
        @DisplayName("deve registrar idioma e histórico assistido")
        void deveRegistrarIdiomaEHistorico() {
            perfil.marcarComoAssistido("before-sunrise");

            assertAll(
                    () -> assertTrue(perfil.getIdiomasAceitos().contains(Idioma.INGLES)),
                    () -> assertTrue(perfil.getHistoricoAssistidos().contains("before-sunrise")),
                    () -> assertFalse(perfil.getIdiomasAceitos().contains(Idioma.JAPONES))
            );
        }
    }
}
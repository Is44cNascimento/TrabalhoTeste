package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
@DisplayName("Filme")
class FilmeTest {

    private Filme filme;

    @BeforeEach
    void setUp() {
        filme = new Filme("matrix", "Matrix", 1999, 136,
                Set.of(Genero.ACAO, Genero.FICCAO_CIENTIFICA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 95);
    }

    @Nested
    @DisplayName("identidade")
    class Identidade {

        @Test
        @DisplayName("deve considerar igualdade apenas pelo id")
        void deveConsiderarIgualdadeApenasPeloId() {
            Filme mesmoId = new Filme("matrix", "Matrix Reloaded", 2003, 138,
                    Set.of(Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 70);
            Filme outroId = new Filme("matrix-2", "Matrix Reloaded", 2003, 138,
                    Set.of(Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 70);

            assertAll(
                    () -> assertEquals(filme, mesmoId),
                    () -> assertEquals(filme.hashCode(), mesmoId.hashCode()),
                    () -> assertNotEquals(filme, outroId)
            );
        }
    }

    @Nested
    @DisplayName("estado imutável")
    class EstadoImutavel {

        @Test
        @DisplayName("deve expor os dados do filme corretamente")
        void deveExporDadosCorretamente() {
            Genero[] generos = filme.getGeneros().stream().sorted(Comparator.comparing(Enum::name)).toArray(Genero[]::new);

            assertAll(
                    () -> assertEquals("matrix", filme.getId()),
                    () -> assertEquals("Matrix", filme.getTitulo()),
                    () -> assertArrayEquals(new Genero[]{Genero.ACAO, Genero.FICCAO_CIENTIFICA}, generos),
                    () -> assertEquals(ClassificacaoEtaria.QUATORZE, filme.getClassificacaoEtaria())
            );
        }

        @Test
        @DisplayName("deve impedir mutação da coleção de gêneros")
        void deveImpedirMutacaoDosGeneros() {
            assertThrows(UnsupportedOperationException.class, () -> filme.getGeneros().add(Genero.DRAMA));
        }

        @Test
        @DisplayName("deve validar popularidade")
        void deveValidarPopularidade() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> new Filme("id", "Título", 2020, 100, Set.of(Genero.DRAMA),
                            ClassificacaoEtaria.DOZE, Idioma.PORTUGUES, 101));

            assertTrue(exception.getMessage().contains("Popularidade"));
        }
    }
}
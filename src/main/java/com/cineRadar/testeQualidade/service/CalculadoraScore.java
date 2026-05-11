package com.cineRadar.testeQualidade.service;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.PerfilCinefilo;
import com.cineRadar.testeQualidade.model.Usuario;
import com.cineRadar.testeQualidade.model.enums.Genero;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadoraScore {

    public static final double PESO_GENERO = 50.0;
    public static final double PESO_DURACAO = 20.0;
    public static final double PESO_POPULARIDADE = 15.0;
    public static final double PESO_AFINIDADE = 15.0;
    private static final double JANELA_TOLERANCIA_DURACAO_MINUTOS = 60.0;

    public int calcularScore(Usuario usuario, Filme filme, List<Filme> catalogoCompleto) {
        Objects.requireNonNull(usuario, "usuario não pode ser null");
        Objects.requireNonNull(filme, "filme não pode ser null");

        PerfilCinefilo perfil = usuario.getPerfilCinefilo();
        double score = calcularComponenteGenero(perfil, filme)
                + calcularComponenteDuracao(perfil, filme)
                + calcularComponentePopularidade(filme)
                + calcularComponenteAfinidade(perfil, filme, catalogoCompleto == null ? Collections.emptyList() : catalogoCompleto);
        return (int) Math.round(Math.max(0.0, Math.min(100.0, score)));
    }

    public String gerarJustificativa(Usuario usuario, Filme filme, List<Filme> catalogoCompleto) {
        PerfilCinefilo perfil = usuario.getPerfilCinefilo();
        boolean duracaoPreferida = filme.getDuracaoMinutos() >= perfil.getDuracaoMinimaPreferida()
                && filme.getDuracaoMinutos() <= perfil.getDuracaoMaximaPreferida();
        boolean possuiAfinidade = calcularComponenteAfinidade(perfil, filme,
                catalogoCompleto == null ? Collections.emptyList() : catalogoCompleto) > 0.0;
        return String.format("Gêneros alinhados, duração %s e popularidade %d/100%s.",
                duracaoPreferida ? "dentro da faixa" : "próxima da faixa",
                filme.getPopularidade(),
                possuiAfinidade ? ", com bônus por histórico positivo" : "");
    }

    private double calcularComponenteGenero(PerfilCinefilo perfil, Filme filme) {
        double mediaPesos = filme.getGeneros().stream()
                .mapToDouble(perfil::getPesoGenero)
                .average()
                .orElse(0.0);
        return mediaPesos * PESO_GENERO;
    }

    private double calcularComponenteDuracao(PerfilCinefilo perfil, Filme filme) {
        int duracao = filme.getDuracaoMinutos();
        if (duracao >= perfil.getDuracaoMinimaPreferida() && duracao <= perfil.getDuracaoMaximaPreferida()) {
            return PESO_DURACAO;
        }

        int distancia = 0;
        if (duracao < perfil.getDuracaoMinimaPreferida()) {
            distancia = perfil.getDuracaoMinimaPreferida() - duracao;
        } else if (duracao > perfil.getDuracaoMaximaPreferida()) {
            distancia = duracao - perfil.getDuracaoMaximaPreferida();
        }

        double fator = Math.max(0.0, 1.0 - (distancia / JANELA_TOLERANCIA_DURACAO_MINUTOS));
        return fator * PESO_DURACAO;
    }

    private double calcularComponentePopularidade(Filme filme) {
        return (filme.getPopularidade() / 100.0) * PESO_POPULARIDADE;
    }

    private double calcularComponenteAfinidade(PerfilCinefilo perfil, Filme filme, List<Filme> catalogoCompleto) {
        List<Double> afinidades = catalogoCompleto.stream()
                .filter(catalogado -> perfil.getNotaPara(catalogado.getId()) != null)
                .filter(catalogado -> compartilhaGenero(catalogado, filme))
                .map(catalogado -> perfil.getNotaPara(catalogado.getId()))
                .filter(Objects::nonNull)
                .map(nota -> (nota - 1) / 4.0)
                .toList();

        if (afinidades.isEmpty()) {
            return 0.0;
        }

        double media = afinidades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return media * PESO_AFINIDADE;
    }

    private boolean compartilhaGenero(Filme base, Filme candidato) {
        Optional<Genero> compartilhado = base.getGeneros().stream()
                .filter(candidato.getGeneros()::contains)
                .findFirst();
        return compartilhado.isPresent();
    }
}

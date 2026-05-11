package com.cineRadar.testeQualidade.model;

import java.util.Objects;

public final class Recomendacao {

    private final Filme filme;
    private final int score;
    private final String justificativa;

    public Recomendacao(Filme filme, int score, String justificativa) {
        this.filme = Objects.requireNonNull(filme, "filme não pode ser null");
        this.score = Math.max(0, Math.min(100, score));
        this.justificativa = Objects.requireNonNull(justificativa, "justificativa não pode ser null");
    }

    public Filme getFilme() {
        return filme;
    }

    public int getScore() {
        return score;
    }

    public String getJustificativa() {
        return justificativa;
    }
}

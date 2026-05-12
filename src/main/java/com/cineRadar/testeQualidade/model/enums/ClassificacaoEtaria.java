package com.cineRadar.testeQualidade.model.enums;

public enum ClassificacaoEtaria {

    LIVRE(0),
    DEZ(10),
    DOZE(12),
    QUATORZE(14),
    DEZESSEIS(16),
    DEZOITO(18);

    private final int idadeMinima;

    ClassificacaoEtaria(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }


    public int getIdadeMinima() {
        return idadeMinima;
    }

    public boolean permiteIdade(int idade) {
        return idade >= idadeMinima;
    }

    public boolean aceita(ClassificacaoEtaria outra) {
        return outra.idadeMinima <= this.idadeMinima;
    }

    public boolean maisRestritivaQue(ClassificacaoEtaria outra) {
        return this.idadeMinima > outra.idadeMinima;
    }
}
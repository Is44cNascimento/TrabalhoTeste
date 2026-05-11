package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.exception.DuracaoInvalidaException;
import com.cineRadar.testeQualidade.exception.NotaInvalidaException;
import com.cineRadar.testeQualidade.exception.PesoInvalidoException;
import com.cineRadar.testeQualidade.model.enums.ClassificacaoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;
import com.cineRadar.testeQualidade.model.enums.Idioma;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PerfilCinefilo {

    private final Map<Genero, Double> pesosGenero = new EnumMap<>(Genero.class);
    private int duracaoMinimaPreferida = 0;
    private int duracaoMaximaPreferida = Integer.MAX_VALUE;
    private ClassificacaoEtaria classificacaoEtariaMaxima = ClassificacaoEtaria.DEZOITO;
    private final Set<Idioma> idiomasAceitos = new LinkedHashSet<>();
    private final Set<String> historicoAssistidos = new LinkedHashSet<>();
    private final Map<String, Integer> notasPorFilme = new java.util.LinkedHashMap<>();

    public void definirPesoGenero(Genero genero, double peso) {
        Objects.requireNonNull(genero, "genero não pode ser null");
        if (peso < 0.0 || peso > 1.0) {
            throw new PesoInvalidoException("O peso deve estar entre 0.0 e 1.0");
        }
        pesosGenero.put(genero, peso);
    }

    public double getPesoGenero(Genero genero) {
        return pesosGenero.getOrDefault(genero, 0.0);
    }

    public void definirFaixaDuracao(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new DuracaoInvalidaException("A duração mínima não pode ser maior que a máxima");
        }
        this.duracaoMinimaPreferida = minimo;
        this.duracaoMaximaPreferida = maximo;
    }

    public void definirClassificacaoEtariaMaxima(ClassificacaoEtaria classificacaoEtariaMaxima) {
        this.classificacaoEtariaMaxima = Objects.requireNonNull(classificacaoEtariaMaxima,
                "classificacaoEtariaMaxima não pode ser null");
    }

    public void adicionarIdiomaAceito(Idioma idioma) {
        idiomasAceitos.add(Objects.requireNonNull(idioma, "idioma não pode ser null"));
    }

    public void marcarComoAssistido(String filmeId) {
        historicoAssistidos.add(validarFilmeId(filmeId));
    }

    public void registrarNota(String filmeId, int nota) {
        validarNota(nota);
        notasPorFilme.put(validarFilmeId(filmeId), nota);
    }

    public Integer getNotaPara(String filmeId) {
        if (filmeId == null) {
            return null;
        }
        return notasPorFilme.get(filmeId);
    }

    public int getDuracaoMinimaPreferida() {
        return duracaoMinimaPreferida;
    }

    public int getDuracaoMaximaPreferida() {
        return duracaoMaximaPreferida;
    }

    public ClassificacaoEtaria getClassificacaoEtariaMaxima() {
        return classificacaoEtariaMaxima;
    }

    public Set<Idioma> getIdiomasAceitos() {
        return Collections.unmodifiableSet(idiomasAceitos);
    }

    public Set<String> getHistoricoAssistidos() {
        return Collections.unmodifiableSet(historicoAssistidos);
    }

    public Map<String, Integer> getNotasPorFilme() {
        return Collections.unmodifiableMap(notasPorFilme);
    }

    public Map<Genero, Double> getPesosGenero() {
        return Collections.unmodifiableMap(pesosGenero);
    }

    private static String validarFilmeId(String filmeId) {
        if (filmeId == null || filmeId.isBlank()) {
            throw new IllegalArgumentException("filmeId não pode ser vazio");
        }
        return filmeId;
    }

    private static void validarNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException("A nota deve estar entre 1 e 5");
        }
    }
}

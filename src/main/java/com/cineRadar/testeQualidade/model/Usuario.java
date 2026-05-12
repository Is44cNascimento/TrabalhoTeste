package com.cineRadar.testeQualidade.model;

import java.util.ArrayList;

public class Usuario {
    private String nome;
    private int idade;
    private PerfilCinefilo perfilCinefilo;
    protected ArrayList<Filme> filmeAssistido = new ArrayList<>();


    public Usuario(String nome, int idade, final PerfilCinefilo perfilCinefilo) {
        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = perfilCinefilo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public void setPerfilCinefilo(PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Filme> getFilmeAssistido() {
        return filmeAssistido;
    }

    public Boolean Assistir (Filme filme){
        if (filme.getClassificacaoEtaria().permiteIdade(this.idade)) {
            addFilmeAssistido(filme);
            return true;
        }
        return false;
    }


    public void addFilmeAssistido(Filme filme) {
        filmeAssistido.add(filme);
    }

    public void retunPerfil(){
        System.out.println("Perfil retornado");
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade);
        System.out.println("PerfilCinefilo: " + this.perfilCinefilo);
        System.out.println("FilmeAssistido: " + filmeAssistido);
    }

    public boolean isNotificacoesHabilitadas() {
        return true;
    }
}

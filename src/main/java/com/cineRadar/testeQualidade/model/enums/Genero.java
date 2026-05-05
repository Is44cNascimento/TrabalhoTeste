package com.cineRadar.testeQualidade.model.enums;

public class  Genero {
    private String nome;


    public  Genero(String nome) {

        this.nome = nome;
    }
    

     public Genero terror = new  Genero("Terror");
     public Genero comedia = new  Genero("Comédia");
     public Genero acao = new  Genero("Ação");
     public Genero aventura = new  Genero("Aventura");
     public Genero ficçãoCientifica = new  Genero("Ficção Científica");

}

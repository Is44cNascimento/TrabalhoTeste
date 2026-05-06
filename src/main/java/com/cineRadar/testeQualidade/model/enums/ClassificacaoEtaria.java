package com.cineRadar.testeQualidade.model.enums;

import com.cineRadar.testeQualidade.model.Filme;
import com.cineRadar.testeQualidade.model.Usuario;

public class ClassificacaoEtaria {

    private Usuario usuario;
    private Filme filme;

    public ClassificacaoEtaria() {
    }

    public ClassificacaoEtaria(Usuario usuario, Filme filme) {
        this.usuario = usuario;
        this.filme = filme;
    }

    public boolean classificar() {
       if(usuario.getIdade() <= filme.getIdade()){
           return true;
       }else return false;

    }

}

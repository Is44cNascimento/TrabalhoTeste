package com.cineRadar.testeQualidade.model;

import com.cineRadar.testeQualidade.model.enums.ClassificaçãoEtaria;
import com.cineRadar.testeQualidade.model.enums.Genero;

public class PerfilCinefilo {
    private Genero genero;
    private ClassificaçãoEtaria classificaçãoEtaria;

    public PerfilCinefilo(Genero genero, ClassificaçãoEtaria classificaçãoEtaria) {
        this.genero = genero;
        this.classificaçãoEtaria = classificaçãoEtaria;
    }

}

package com.cineRadar.testeQualidade.service;
import com.cineRadar.testeQualidade.model.Recomendacao;
import com.cineRadar.testeQualidade.model.Usuario;

import java.util.List;

public interface NotificadorPush {

    void enviar(Usuario usuario, List<Recomendacao> recomendacoes);
}
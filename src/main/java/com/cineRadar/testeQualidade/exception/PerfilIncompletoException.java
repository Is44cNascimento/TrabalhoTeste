package com.cineRadar.testeQualidade.exception;


/**
Caso o perfil do usuário esteja incompleto, ou seja,
faltem informações essenciais para a recomendação de filmes, como:
-gênero preferido
-idade
 histórico de avaliações
-nome

 O sistema deve lançar uma exceção personalizada chamada PerfilIncompletoException.
Essa exceção deve ser tratada adequadamente para informar ao usuário sobre a
necessidade de completar seu perfil antes de receber recomendações personalizadas.
 **/
public class PerfilIncompletoException extends Exception {
    private static final String MENSAGEM_PADRAO = "Perfil do usuário incompleto. Por favor, complete seu perfil para receber recomendações personalizadas.";

    public PerfilIncompletoException() {
        super(MENSAGEM_PADRAO);
    }

    public PerfilIncompletoException(String message) {
        super(message);
    }

    public PerfilIncompletoException(Throwable cause) {
        super(MENSAGEM_PADRAO, cause);
    }

    public PerfilIncompletoException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String retorno(Exception exception) {
        if (exception == null) {
            return "";
        }
        if (exception instanceof NumberFormatException || exception.getCause() instanceof NumberFormatException) {
            return MENSAGEM_PADRAO;
        }
        String msg = exception.getMessage();
        return msg != null ? msg : "";
    }

}

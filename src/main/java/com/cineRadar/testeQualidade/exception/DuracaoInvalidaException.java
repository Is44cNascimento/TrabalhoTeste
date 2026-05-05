package com.cineRadar.testeQualidade.exception;

public class DuracaoInvalidaException extends Exception {

    private static final String MENSAGEM_PADRAO = "Tempo de duração do filme excede o limite mínimo.";

    public DuracaoInvalidaException() {
        super(MENSAGEM_PADRAO);
    }

    public DuracaoInvalidaException(String message) {
        super(message);
    }

    public DuracaoInvalidaException(Throwable cause) {
        super(MENSAGEM_PADRAO, cause);
    }

    public DuracaoInvalidaException(String message, Throwable cause) {
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

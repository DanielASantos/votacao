package com.cooperativa.votacao.exception;

public class IntegracaoExternaException extends RuntimeException {

    public IntegracaoExternaException(String message, Throwable causa) { super(message, causa); }
}
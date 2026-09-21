package com.cooperativa.votacao.exception;

public class SalvarPautaException extends RuntimeException {

    public SalvarPautaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

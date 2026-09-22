package com.cooperativa.votacao.exception;

public class ErroAoSalvarException extends RuntimeException {

    public ErroAoSalvarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

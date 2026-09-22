package com.cooperativa.votacao.exception;

public class RegistroNaoEncontradoException extends RuntimeException{

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

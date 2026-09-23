package com.cooperativa.votacao.exception;

public class CpfInvalidoException extends RuntimeException {

    public CpfInvalidoException(String message, Throwable causa) { super(message, causa); }
}
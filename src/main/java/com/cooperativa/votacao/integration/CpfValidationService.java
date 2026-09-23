package com.cooperativa.votacao.integration;

public interface CpfValidationService {

    boolean isEligibleToVote(String cpf);
}
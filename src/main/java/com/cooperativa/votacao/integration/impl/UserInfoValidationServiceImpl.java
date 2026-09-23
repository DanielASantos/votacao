package com.cooperativa.votacao.integration.impl;

import com.cooperativa.votacao.dto.response.CpfStatusResponse;
import com.cooperativa.votacao.exception.CpfInvalidoException;
import com.cooperativa.votacao.exception.IntegracaoExternaException;
import com.cooperativa.votacao.integration.CpfValidationService;
import com.cooperativa.votacao.integration.client.UserInfoFeignClient;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoValidationServiceImpl implements CpfValidationService {

    private final UserInfoFeignClient feignClient;

    public UserInfoValidationServiceImpl(UserInfoFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public boolean isEligibleToVote(String cpf) {
        try {
            CpfStatusResponse response = feignClient.verificarStatus(cpf);
            return "ABLE_TO_VOTE".equalsIgnoreCase(response.status());

        } catch (FeignException.NotFound e) {
            throw new CpfInvalidoException("O CPF informado é inválido ou não foi encontrado.");

        } catch (FeignException e) {
            throw new IntegracaoExternaException("Serviço de validação de CPF indisponível no momento.");
        }
    }
}

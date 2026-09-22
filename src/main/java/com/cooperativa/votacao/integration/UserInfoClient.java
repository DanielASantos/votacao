package com.cooperativa.votacao.integration;

import com.cooperativa.votacao.dto.response.CpfStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userInfoClient", url = "https://user-info.herokuapp.com")
public interface UserInfoClient {

    @GetMapping("/users/{cpf}")
    CpfStatusResponse verificarStatus(@PathVariable("cpf") String cpf);
}

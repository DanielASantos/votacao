package com.cooperativa.votacao.integration.client;

import com.cooperativa.votacao.dto.response.CpfStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "userInfoClient",
        url = "${app.integration.user-info.url:https://user-info.herokuapp.com}"
)
public interface UserInfoFeignClient {

    @GetMapping("/users/{cpf}")
    CpfStatusResponse verificarStatus(@PathVariable("cpf") String cpf);
}


package com.springboot.seataorderservice.feignclient;

import com.springboot.seataaccountservice.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountFeignClient {
    @GetMapping("/account/selectOne")
    Account selectOne(@RequestParam("id") Long id);

    @PostMapping("/account/insert")
    Account insert(@RequestBody Account account);

    @PostMapping("/account/update")
    Account update(@RequestBody Account account);

}

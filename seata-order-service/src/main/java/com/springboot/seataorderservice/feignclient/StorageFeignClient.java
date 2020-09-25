package com.springboot.seataorderservice.feignclient;

import com.springboot.seatastorageservice.entity.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service")
public interface StorageFeignClient {
    @GetMapping("/storage/selectOne")
    Storage selectOne(@RequestParam("id") Long id);

    @PostMapping("/storage/insert")
    Storage insert(@RequestBody Storage storage);

    @PostMapping("/storage/update")
    Storage update(@RequestBody Storage storage);

}

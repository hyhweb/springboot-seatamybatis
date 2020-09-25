package com.springboot.seataorderservice.feignclient.fallback;


import com.springboot.seataorderservice.feignclient.StorageFeignClient;
import com.springboot.seatastorageservice.entity.Storage;
import org.springframework.stereotype.Component;

@Component
public class StorageFallback implements StorageFeignClient {
    @Override
    public Storage selectOne(Long id) {
        return null;
    }

    @Override
    public Storage insert(Storage storage) {
        return null;
    }

    @Override
    public Storage update(Storage storage) {
        return null;
    }
}

package com.springboot.seataorderservice.controller;


import com.springboot.seataaccountservice.entity.Account;
import com.springboot.seataorderservice.entity.Order;
import com.springboot.seataorderservice.feignclient.AccountFeignClient;
import com.springboot.seataorderservice.feignclient.StorageFeignClient;
import com.springboot.seataorderservice.service.OrderService;
import com.springboot.seatastorageservice.entity.Storage;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * (Order)表控制层
 *
 * @author hyhong
 * @since 2020-07-03 14:28:24
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    private Lock lock = new ReentrantLock();

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Order selectOne(Long id) {
        return this.orderService.queryById(id);
    }

    @GetMapping("/insert")
    @GlobalTransactional(name = "my_test_tx_group", rollbackFor = Exception.class)
    public Object insert() throws TransactionException  {

        try {
            lock.lock();

            orderService.insertOrder();

            return "添加成功";
        } catch (Exception e) {
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
            return "添加失败";
        } finally {
            lock.unlock();
        }

    }

}

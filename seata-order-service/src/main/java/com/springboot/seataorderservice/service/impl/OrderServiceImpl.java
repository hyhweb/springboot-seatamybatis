package com.springboot.seataorderservice.service.impl;


import com.springboot.seataaccountservice.entity.Account;
import com.springboot.seataorderservice.dao.OrderDao;
import com.springboot.seataorderservice.entity.Order;
import com.springboot.seataorderservice.feignclient.AccountFeignClient;
import com.springboot.seataorderservice.feignclient.StorageFeignClient;
import com.springboot.seataorderservice.service.OrderService;
import com.springboot.seatastorageservice.entity.Storage;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Order)表服务实现类
 *
 * @author hyhong
 * @since 2020-07-03 14:28:24
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;
    @Autowired
    private AccountFeignClient accountFeignClient;
    @Autowired
    private StorageFeignClient storageFeignClient;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Order queryById(Long id) {
        return this.orderDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Order> queryAllByLimit(int offset, int limit) {
        return this.orderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order insert(Order order) {
        this.orderDao.insert(order);
        return order;
    }

    @Override
    public Order insertOrder()    {
        System.out.println("-----------------" + RootContext.getXID());
        Order order = new Order();
        order.setUserId(Long.valueOf(1));
        order.setProductId(Long.valueOf(1));
        order.setCount(10);
        order.setMoney(Double.valueOf(10));
        order.setStatus(0);
        orderService.insert(order);
        Account account = accountFeignClient.selectOne(order.getUserId());
        Account newAccount = new Account();
        newAccount.setUserId(order.getUserId());
        newAccount.setUsed(account.getUsed() + order.getMoney());
        newAccount.setResidue(account.getResidue() - order.getMoney());
        accountFeignClient.update(newAccount);

        Storage storage = storageFeignClient.selectOne(order.getProductId());
        Storage newStorage = new Storage();
        newStorage.setProductId(order.getProductId());
        newStorage.setUsed(storage.getUsed() + order.getCount());
        newStorage.setResidue(storage.getResidue() - order.getCount());
        storageFeignClient.update(newStorage);
        order.setStatus(1);
        orderService.update(order);
        return order;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order update(Order order) {
        this.orderDao.update(order);
        return this.queryById(order.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderDao.deleteById(id) > 0;
    }
}

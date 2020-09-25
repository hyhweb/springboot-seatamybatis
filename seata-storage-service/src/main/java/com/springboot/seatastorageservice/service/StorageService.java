package com.springboot.seatastorageservice.service;


import com.springboot.seatastorageservice.entity.Storage;

import java.util.List;

/**
 * (Storage)表服务接口
 *
 * @author hyhong
 * @since 2020-06-30 18:02:32
 */
public interface StorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Storage queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Storage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    Storage insert(Storage storage);

    /**
     * 修改数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    Storage update(Storage storage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}

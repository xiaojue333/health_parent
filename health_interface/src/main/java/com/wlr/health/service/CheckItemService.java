package com.wlr.health.service;

import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 查询所有检测项
     *
     * @return
     */
    List<CheckItem> findAll();

    //添加检查项
    void add(CheckItem checkItem);

    //删除检查项     需要抛出异常,否则dubbo无法检测出自定义异常
    void deleteById(Integer id) throws MyException;

    //修改检查项
    void update(CheckItem checkItem);

    //分页查询检查项
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
}

package com.wlr.health.service;


import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    //新增检查组
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    //分页查询检查组
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    //根据检查组id查找组
    CheckGroup findGroupById(Integer id);

    //根据检查组id查找关联检查项id
    List<Integer> findItemById(Integer id);

    //修改检查组
    void update(CheckGroup checkGroup, Integer[] checkItemIds);

    //根据id删除检查组     逻辑错误抛自定义异常
    void deleteById(Integer id) throws MyException;
}

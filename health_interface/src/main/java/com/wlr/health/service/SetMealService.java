package com.wlr.health.service;

import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.Setmeal;

import java.util.List;

public interface SetMealService {

    //添加套餐
    void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds);

    //分页查询套餐
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //根据id查询套餐
    Setmeal findById(Integer id);

    //获取套餐关联的检查组id
    List<Integer> findCheckGroupById(Integer id);

    //修改套餐
    void update(Setmeal setmeal, Integer[] checkGroupIds);

    //根据id删除套餐
    void deleteById(Integer id) throws MyException;
}

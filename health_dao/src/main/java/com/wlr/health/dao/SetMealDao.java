package com.wlr.health.dao;

import com.github.pagehelper.Page;
import com.wlr.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetMealDao {

    //插入套餐数据    返回id
    void addSetMeal(Setmeal setmeal);

    //将关联组id插入到关系表中
    void addSetMealAndCheckGroupId(@Param("setMealId") Integer setMealId, @Param("checkGroupId") Integer checkGroupId);

    //查询套餐列表
    Page<Setmeal> findPage(String queryString);

    //根据id查询套餐
    Setmeal findById(Integer id);

    //获取套餐关联的检查组id
    List<Integer> findCheckGroupById(Integer id);

    //修改套餐
    void update(Setmeal setmeal);

    //根据套餐id删除关系表中的所有检查组项
    void removeCheckGroupId(Integer id);

    //查询套餐是否有绑定检查组
    int findOrderBySetMealId(Integer id);

    //删除套餐
    void deleteById(Integer id);
}

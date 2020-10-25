package com.wlr.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.wlr.health.dao.SetMealDao;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.Setmeal;
import com.wlr.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    //添加套餐  增加事务
    @Transactional
    @Override
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds) {
        //插入套餐数据    返回id
        setMealDao.addSetMeal(setmeal);

        //获取套餐id
        Integer id = setmeal.getId();

        if (checkGroupIds != null) {
            //依次将关联组id插入到关系表中
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealAndCheckGroupId(id, checkGroupId);
            }
        }

    }

    //分页查询套餐
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //使用分页插件设置页面页显示数    之后查询会被分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //判断是否需要模糊查询
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        //查询套餐列表
        Page<Setmeal> page = setMealDao.findPage(queryPageBean.getQueryString());

        return new PageResult<>(page.getTotal(), page.getResult());
    }

    //根据id查询套餐
    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    //获取套餐关联的检查组id
    @Override
    public List<Integer> findCheckGroupById(Integer id) {
        return setMealDao.findCheckGroupById(id);
    }

    //修改套餐  添加事务
    @Transactional
    @Override
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        //修改套餐
        setMealDao.update(setmeal);
        Integer id = setmeal.getId();

        //根据套餐id删除关系表中的所有检查组项
        setMealDao.removeCheckGroupId(id);

        if (checkGroupIds != null) {
            //遍历检查组依次根据套餐id添加到关系表中
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealAndCheckGroupId(id, checkGroupId);
            }
        }
    }

    //根据id删除套餐  开启事务
    @Transactional
    @Override
    public void deleteById(Integer id) throws MyException {
        //查询套餐是否有绑定检查组
        int row = setMealDao.findOrderBySetMealId(id);

        if (row > 0) {
            throw new MyException("套餐已被检查组使用,无法删除套餐");
        }

        //删除与套餐绑定的检查组关系表数据
        setMealDao.removeCheckGroupId(id);

        //删除套餐
        setMealDao.deleteById(id);
    }
}

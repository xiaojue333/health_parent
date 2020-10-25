package com.wlr.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.wlr.health.dao.CheckGroupDao;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.CheckGroup;
import com.wlr.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增检查组
     * 开启事务
     *
     * @param checkGroup
     * @param checkItemIds
     */
    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //添加检查组数据到表中    并返回键
        checkGroupDao.add(checkGroup);

        //获取检查组id
        Integer id = checkGroup.getId();

        //通过检查组id将选中的检查项id依次绑定到关系表中
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.addCheckItemById(id, checkItemId);
            }
        }
    }

    //分页查询检查组数据
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //分页插件  页码和每页显示数    之后执行的代码会被分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {//判空
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");//拼接模糊查询%关键字%
        }

        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());

        return new PageResult<>(page.getTotal(), page.getResult());
    }

    //根据id查找检查组
    @Override
    public CheckGroup findGroupById(Integer id) {

        return checkGroupDao.findGroupById(id);
    }

    //根据检查组id查找关联检查项id
    @Override
    public List<Integer> findItemById(Integer id) {

        return checkGroupDao.findItemById(id);
    }

    //修改检查组与关联检查项 添加事务
    @Transactional
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkItemIds) {
        //修改检查组
        checkGroupDao.update(checkGroup);

        //删除关系表关联的检查项id
        checkGroupDao.deleteCheckItemId(checkGroup.getId());
        Integer id = checkGroup.getId();

        //添加新的关系表关联的检查项id
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.addCheckItemById(id, checkItemId);
            }
        }
    }

    //根据id删除检查组     逻辑错误抛自定义异常
    @Transactional//添加事务
    @Override
    public void deleteById(Integer id) throws MyException {
        //查询组是否有绑定套餐
        int cou = checkGroupDao.findSetmealByCheckGroupId(id);

        if (cou > 0) {
            throw new MyException("无法删除绑定套餐的检查组");
        }

        //删除组与组关联的关系表中的数据
        checkGroupDao.deleteCheckItemId(id);

        //根据id删除组
        checkGroupDao.deleteById(id);
    }
}

package com.wlr.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.wlr.health.dao.CheckItemDao;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.CheckItem;
import com.wlr.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * Description: No Description
 * 解决 dubbo 2.6.0 【注意，注意，注意】
 * interfaceClass 发布出去服务的接口为这个CheckItemService.class
 * 没加interfaceClass, 调用No Provider 的异常
 * User: Eric
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckImplServiceImpl implements CheckItemService {
    @Autowired
    CheckItemDao checkitemDao;

    //查询所有检测项
    @Override
    public List<CheckItem> findAll() {
        return checkitemDao.findAll();
    }

    //添加检测项
    @Override
    public void add(CheckItem checkItem) {
        checkitemDao.add(checkItem);
    }

    //删除检查项
    @Override
    public void deleteById(Integer id) {
        //查询检查项是否有绑定检查组
        int cou = checkitemDao.findByCheckItemId(id);

        if (cou > 0) {// 已经被检查组使用了，则不能删除，报错
            throw new MyException("已经被检查组使用了,不能删除");
        }

        //根据id删除检查项
        checkitemDao.deleteById(id);
    }

    //修改检查项
    @Override
    public void update(CheckItem checkItem) {
        checkitemDao.update(checkItem);
    }

    //分页查询检查项
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        //PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 模糊查询 拼接 %
        // 判断是否有查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        //Page<CheckItem> page = checkitemDao.findPage(queryPageBean.getQueryString());

        //queryPageBean(当前页码-1)*需要查询数;
        queryPageBean.setCurrentPage((queryPageBean.getCurrentPage() - 1) * queryPageBean.getPageSize());
        List<CheckItem> checkItems = checkitemDao.findPageNumber(queryPageBean);
        Long total = checkitemDao.totalCount();//查询总页数

        // 封装到分页结果对象中
        return new PageResult<>(total, checkItems);
    }
}

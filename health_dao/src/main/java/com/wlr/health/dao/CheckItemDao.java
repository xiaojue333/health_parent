package com.wlr.health.dao;

import com.github.pagehelper.Page;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    //查询所有检测项
    List<CheckItem> findAll();

    //添加检测项
    void add(CheckItem checkItem);

    //删除检查项
    void deleteById(Integer id);

    //修改检查项
    void update(CheckItem checkItem);

    //查询总记录数
    Long totalCount();

    //插件按页码分页查询检查项
    //Page<CheckItem> findPage(String PageBean);

    //按页码分页查询检查项
    List<CheckItem> findPageNumber(QueryPageBean queryPageBean);

    //根据检查项id查询检查组
    int findByCheckItemId(Integer id);
}

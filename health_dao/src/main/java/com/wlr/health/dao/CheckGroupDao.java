package com.wlr.health.dao;

import com.github.pagehelper.Page;
import com.wlr.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckGroupDao {

    //添加检查组数据到表中    并返回键
    void add(CheckGroup checkGroup);

    //通过检查组id将选中的检查项id依次绑定到关系表中     /有相同的基本数据类型需要取别名
    void addCheckItemById(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkItemId);

    //分页/模糊查询检查组
    Page<CheckGroup> findPage(String queryString);

    //根据id查找检查组
    CheckGroup findGroupById(Integer id);

    //根据检查组id查找关联检查项id
    List<Integer> findItemById(Integer id);

    //修改检查组
    void update(CheckGroup checkGroup);

    //删除关系表关联的检查项id
    void deleteCheckItemId(Integer id);

    //查询组是否有绑定套餐
    int findSetmealByCheckGroupId(Integer id);

    //根据id删除组
    void deleteById(Integer id);
}

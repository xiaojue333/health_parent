package com.wlr.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wlr.health.constant.MessageConstant;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.entity.Result;
import com.wlr.health.pojo.CheckGroup;
import com.wlr.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupSercice;

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupSercice.add(checkGroup, checkItemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * //分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage.do")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> groupPageResult = checkGroupSercice.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, groupPageResult);
    }

    /**
     * 根据检查组id查找组
     *
     * @param id
     * @return
     */
    @GetMapping("findGroupById")
    public Result findGroupById(Integer id) {
        CheckGroup checkGroup = checkGroupSercice.findGroupById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 根据检查组id查找关联检查项id
     *
     * @param id
     * @return
     */
    @GetMapping("findItemById")
    public Result findItemById(Integer id) {
        List<Integer> ids = checkGroupSercice.findItemById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, ids);
    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupSercice.update(checkGroup, checkItemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //根据id删除检查组
    @GetMapping("deleteById")
    public Result deleteById(Integer id) {
        checkGroupSercice.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有检查组数据
     * @return
     */
    @GetMapping("findAll")
    public Result findAll() {
        List<CheckGroup> list = checkGroupSercice.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }
}

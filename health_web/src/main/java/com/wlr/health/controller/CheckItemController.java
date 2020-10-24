package com.wlr.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wlr.health.constant.MessageConstant;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.entity.Result;
import com.wlr.health.pojo.CheckItem;
import com.wlr.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("checkItem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    /**
     * 获取所有检查项
     *
     * @return
     */
    @GetMapping("findAll")
    public Result findAll() {
        List<CheckItem> checkItems = checkItemService.findAll();

        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItems);
    }

    /**
     * 添加检查项
     *
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     *
     * @return
     */
    @RequestMapping("delete")
    public Result deleteById(Integer id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询检查项
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 修改检查项
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody CheckItem checkItem) {
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}

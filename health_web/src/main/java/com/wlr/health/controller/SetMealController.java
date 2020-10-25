package com.wlr.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wlr.health.constant.MessageConstant;
import com.wlr.health.entity.PageResult;
import com.wlr.health.entity.QueryPageBean;
import com.wlr.health.entity.Result;
import com.wlr.health.pojo.Setmeal;
import com.wlr.health.service.SetMealService;
import com.wlr.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("setMeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    /**
     * 上传图片返回链接
     */
    @PostMapping("upload")
    public Result uploadPicture(MultipartFile imgFile) {
        String filename = imgFile.getOriginalFilename();//获取图片文件名
        String imgName = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));//随机生成文件名

        //上传图片到七牛云
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), imgName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
        }

        //创建map储存图片链接与名字
        Map<String, Object> map = new HashMap<>();
        map.put("domain", QiNiuUtils.DOMAIN);
        map.put("imgName", imgName);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
    }

    /**
     * 添加套餐
     */
    @PostMapping("add")
    public Result addSetMeal(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        setMealService.addSetMeal(setmeal, checkGroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询套餐
     */
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setMealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 根据id查询套餐
     */
    @GetMapping("findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setMealService.findById(id);

        //将查询的套餐与七牛云仓库地址存储到map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("setMeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }

    /**
     * 获取套餐关联的检查组id
     */
    @GetMapping("findCheckGroupById")
    public Result findCheckGroupById(Integer id) {
        List<Integer> list = setMealService.findCheckGroupById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

    /**
     * 修改套餐
     */
    @PostMapping("update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        setMealService.update(setmeal, checkGroupIds);
        return new Result(true, "修改套餐成功");
    }

    /**
     *  根据id删除套餐
     */
    @PostMapping("deleteById")
    public Result deleteById(Integer id){
        setMealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }

}

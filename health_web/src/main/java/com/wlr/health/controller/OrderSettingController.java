package com.wlr.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wlr.health.constant.MessageConstant;
import com.wlr.health.entity.Result;
import com.wlr.health.pojo.OrderSetting;
import com.wlr.health.service.OrderSettingService;
import com.wlr.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orderSetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    /**
     * 批量导入预约设置信息添加数据
     *
     * @param excelFile
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    public Result upload(MultipartFile excelFile) throws Exception {
        try {
            //调用工具类将获取的文件转换成字符串数组集合
            List<String[]> stringList = POIUtils.readExcel(excelFile);

            //添加对象到集合    //获取简单日期格式对象
            List<OrderSetting> orderSettings = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);

            //将字符串数组转换成实体类对象
            for (String[] strings : stringList) {
                //通过解析字符串获取对应日期对象
                Date date = sdf.parse(strings[0]);

                //将预约人数字符串转换成数值
                int number = Integer.parseInt(strings[1]);

                //添加对象
                orderSettings.add(new OrderSetting(date, number));
            }

            orderSettingService.upload(orderSettings);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error(MessageConstant.IMPORT_ORDERSETTING_FAIL, e);
            throw e;
        }
    }

    /**
     * 获取当前年月份所有预约数据
     */
    @GetMapping("findByMonth")
    public Result findByMonth(String month) {
        List<Map<String, Integer>> list = orderSettingService.findByMonth(month);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
    }

    /**
     * 按日期查询更新可预约数
     */
    @PostMapping("updateNumberByDate")
    public Result updateNumberByDate(@RequestBody OrderSetting orderSetting) {
        ArrayList<OrderSetting> orderSettings = new ArrayList<>();
        orderSettings.add(orderSetting);
        orderSettingService.upload(orderSettings);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}

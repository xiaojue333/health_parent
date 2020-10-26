package com.wlr.health.service;

import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    //批量导入预约设置信息添加数据
    void upload(List<OrderSetting> orderSettings) throws MyException;

    //获取当前年月份所有预约数据
    List<Map<String,Integer>> findByMonth(String month);
}

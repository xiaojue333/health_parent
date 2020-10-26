package com.wlr.health.dao;

import com.wlr.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderSettingDao {

    //根据日期获取数据库中的订单设置数据
    OrderSetting findByDate(Date orderDate);

    //修改可预约人数
    void updateNumber(OrderSetting orderSetting);

    //添加新预约数据
    void add(OrderSetting orderSetting);

    //获取当前年月份所有预约数据
    List<Map<String,Integer>> findByMonth(String month);
}

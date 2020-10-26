package com.wlr.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wlr.health.constant.MessageConstant;
import com.wlr.health.dao.OrderSettingDao;
import com.wlr.health.exception.MyException;
import com.wlr.health.pojo.OrderSetting;
import com.wlr.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置信息添加数据    开启事务
    @Override
    @Transactional
    public void upload(List<OrderSetting> orderSettings) throws MyException {
        //遍历集合依次操作数据库
        for (OrderSetting orderSetting : orderSettings) {

            //获取对象中的日期判断数据库中是否已经存在数据
            OrderSetting os = orderSettingDao.findByDate(orderSetting.getOrderDate());

            if (os != null) {
                //如果已经存在数据则修改数据
                //判断预约数是否小于已预约人数,如果小于则报错
                if (os.getReservations() > orderSetting.getNumber()) {
                    throw new MyException(orderSetting.getOrderDate() + "中可预约人数设置错误,无法小于已预约人数");
                }

                //大于则修改预约数
                orderSettingDao.updateNumber(orderSetting);

            } else {
                //如果不存在则添加
                orderSettingDao.add(orderSetting);
            }
        }
    }

    //获取当前年月份所有预约数据
    @Override
    public List<Map<String, Integer>> findByMonth(String month) {
        month += "%";//拼接模糊查询%
        return orderSettingDao.findByMonth(month);
    }
}

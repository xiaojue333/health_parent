package com.wlr.health.controller;

import com.wlr.health.entity.Result;
import com.wlr.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理类   获取的异常 异常的范围从小到大，类似于try catch中的catch
@RestControllerAdvice
public class HealExceptionAdvice {

    /**
     * info:  打印日志，记录流程性的内容
     * debug: 记录一些重要的数据 id, orderId, userId
     * error: 记录异常的堆栈信息，代替e.printStackTrace();
     * 工作中不能有System.out.println(), e.printStackTrace();
     */
    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    //自定义异常处理
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException me) {

        return new Result(false, me.getMessage());
    }

    //为止异常处理
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生异常",e);
        return new Result(false, "发生未知错误，操作失败，请联系管理员");
    }

}

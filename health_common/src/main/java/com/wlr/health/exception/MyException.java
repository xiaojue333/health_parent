package com.wlr.health.exception;

//自定义异常类    区分业务逻辑异常与系统异常
public class MyException extends RuntimeException{

    public MyException(String message){
        super(message);
    }
}

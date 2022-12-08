package com.auggie.student_server.utils;

import lombok.Data;

/**
 * @program: CourseVueProject
 * @description: 返回码值集
 * @author: kangwei
 * @create: 2022-12-08 21:03
 **/
public enum ResultCode {

    SUCCESS("000000", "成功"),
    ERROR("100000", "失败"),
    ;

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

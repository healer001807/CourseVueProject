package com.auggie.student_server.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CourseVueProject
 * @description: 响应结果集
 * @author: kangwei
 * @create: 2022-12-07 22:23
 **/
@NoArgsConstructor
@Data
public class ResultUtils<T> {
    /**
     * 响应码值
     */
    private String returnCode;
    /**
     * 响应信息
     */
    private String returnMsg;

    /**
     * 返回结果集
     */
    private Object data;


    /**
     * 响应码值
     * 响应信息
     *
     * @param returnCode
     * @param returnMsg
     */
    public ResultUtils(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    /**
     * 响应码值
     * 响应信息
     * 数据
     *
     * @param returnCode
     * @param returnMsg
     * @param data
     */
    public ResultUtils(String returnCode, String returnMsg, Object data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }


    /**
     * 成功
     *
     * @param returnCode
     * @param returnMsg
     * @return
     */
    public static ResultUtils success(String returnCode, String returnMsg) {
        return new ResultUtils(returnCode, returnMsg);
    }

    /**
     * 成功
     *
     * @param returnCode
     * @param returnMsg
     * @param data
     * @return
     */
    public static ResultUtils success(String returnCode, String returnMsg, Object data) {
        return new ResultUtils(returnCode, returnMsg, data);
    }

    /**
     * 失败
     *
     * @param returnCode
     * @param returnMsg
     * @return
     */
    public static ResultUtils error(String returnCode, String returnMsg) {
        return new ResultUtils(returnCode, returnMsg);
    }

    /**
     * 失败
     *
     * @param returnCode
     * @param returnMsg
     * @param data
     * @return
     */
    public static ResultUtils error(String returnCode, String returnMsg, Object data) {
        return new ResultUtils(returnCode, returnMsg, data);
    }
}

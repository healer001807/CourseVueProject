package com.auggie.student_server.utils;

/**
 * @program: CourseVueProject
 * @description: 自定义非空校验
 * @author: kangwei
 * @create: 2022-12-07 22:29
 **/
public class StringUtils {

    /**
     * 判断Integer类型数据
     *
     * @param value
     * @return
     */
    public static boolean isNull(Integer value) {
        if (null == value) {
            return true;
        }
        return false;
    }

}

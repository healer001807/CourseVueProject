package com.auggie.student_server.service;

/**
 * @program: CourseVueProject
 * @description: 国际化配置
 * @author: kangwei
 * @create: 2023-05-10 22:33
 **/
public interface I18nService {

    String getMsg(String key, Object[] val);

    String getMsg(String key);
}

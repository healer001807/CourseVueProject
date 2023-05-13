package com.auggie.student_server.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @program: CourseVueProject
 * @description: 语言解析
 * @author: kangwei
 * @create: 2023-05-10 22:56
 **/
public class LocaleUtils {

    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}

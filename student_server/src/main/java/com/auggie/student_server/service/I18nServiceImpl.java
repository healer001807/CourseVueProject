package com.auggie.student_server.service;


import com.auggie.student_server.utils.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @program: CourseVueProject
 * @description: 国际化配置
 * @author: kangwei
 * @create: 2023-05-10 22:33
 **/
@Service
public class I18nServiceImpl implements I18nService {


    @Autowired
    private MessageSource messageSource;


    @Override
    public String getMsg(String key, Object[] val) {
        return messageSource.getMessage(key, val, Locale.getDefault());
    }

    @Override
    public String getMsg(String key) {
        return messageSource.getMessage(key, null, LocaleUtils.getCurrentLocale());

    }
}
